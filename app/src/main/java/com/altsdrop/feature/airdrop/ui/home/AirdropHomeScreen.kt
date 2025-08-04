package com.altsdrop.feature.airdrop.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.altsdrop.R
import com.altsdrop.feature.airdrop.domain.model.previewAirdrop

@Composable
fun AirdropHomeRoute(
    viewModel: AirdropHomeViewModel = hiltViewModel(),
    navigateToAirdropDetails: (String) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AirdropHomeScreen(
        uiState = uiState,
        onTabSelected = {
            viewModel.onTabSelected(it)
        },
        onFeaturedAirdropCardClicked = navigateToAirdropDetails,
        onAirdropCardClicked = navigateToAirdropDetails
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AirdropHomeScreen(
    uiState: AirdropHomeScreenUiState,
    onTabSelected: (Int) -> Unit = {},
    onFeaturedAirdropCardClicked: (String) -> Unit = {},
    onAirdropCardClicked: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = R.string.title_feature_airdrop),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.width(0.dp))
                    }

                    items(
                        items = uiState.featuredAirdrops,
                        key = { airdrop ->
                            airdrop.airdropId
                        }
                    ) { airdrop ->
                        FeaturedAirdropCard(
                            airdrop = airdrop,
                            onFeaturedAirdropCardClicked = onFeaturedAirdropCardClicked
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            stickyHeader(
                key = "tabs"
            ) {
                TabLayout(
                    tabs = uiState.tabs,
                    selectedTabIndex = uiState.selectedTabIndex,
                    onTabSelected = {
                        onTabSelected(it)
                    }
                )
            }

            val airdrops = when (uiState.selectedTabIndex) {
                0 -> uiState.newAirdrops
                1 -> uiState.highlyRatedAirdrops
                2 -> uiState.exploreAirdrops
                else -> {
                    emptyList()
                }
            }

            items(
                items = airdrops,
                key = { airdrop ->
                    airdrop.airdropId
                }
            ) { airdrop ->
                AirdropCard(
                    airdrop = airdrop,
                    onAirdropCardClicked = onAirdropCardClicked
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun TabLayout(
    tabs: List<Int>,
    selectedTabIndex: Int = 0,
    onTabSelected: (Int) -> Unit = {},
) {
    SecondaryTabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = selectedTabIndex,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        indicator = {
            SecondaryIndicator(
                Modifier.tabIndicatorOffset(selectedTabIndex, matchContentSize = false),
                height = 2.dp, // Indicator thickness
                color = MaterialTheme.colorScheme.onBackground // Custom indicator color
            )
        },
        divider = {}
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                modifier = Modifier.fillMaxWidth(),
                text = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 0.dp),
                        textAlign = TextAlign.Center,
                        text = stringResource(id = title),
                        maxLines = 1,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                selected = selectedTabIndex == index,
                onClick = {
                    onTabSelected(index)
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AirdropHomeScreenPreview() {
    AirdropHomeScreen(
        uiState = AirdropHomeScreenUiState(
            featuredAirdrops = listOf(previewAirdrop, previewAirdrop, previewAirdrop)
        )
    )
}
