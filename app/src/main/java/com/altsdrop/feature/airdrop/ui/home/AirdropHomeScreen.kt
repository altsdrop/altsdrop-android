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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.altsdrop.R
import com.altsdrop.feature.airdrop.domain.model.OfficialLinks
import com.altsdrop.feature.airdrop.domain.model.SocialLinks
import com.altsdrop.feature.airdrop.domain.model.Airdrop

@Composable
fun AirdropHomeRoute(
    viewModel: AirdropHomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AirdropHomeScreen(
        uiState = uiState,
        onTabSelected = {
            viewModel.onTabSelected(it)
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AirdropHomeScreen(
    uiState: AirdropHomeScreenUiState,
    onTabSelected: (Int) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
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
                    items(
                        items = uiState.featuredAirdrops,
                        key = { airdrop ->
                            airdrop.airdropId
                        }
                    ) { airdrop ->
                        FeaturedAirdropCard(airdrop = airdrop)
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

            when (uiState.selectedTabIndex) {
                0 -> {
                    items(
                        items = uiState.newAirdrops,
                        key = { airdrop ->
                            airdrop.airdropId
                        }
                    ) { airdrop ->
                        FeaturedAirdropCard(airdrop = airdrop)
                    }
                }
                1 -> {
                    items(
                        items = uiState.highlyRatedAirdrops,
                        key = { airdrop ->
                            airdrop.airdropId
                        }
                    ) { airdrop ->
                        FeaturedAirdropCard(airdrop = airdrop)
                    }
                }
                2 -> {
                    items(
                        items = uiState.exploreAirdrops,
                        key = { airdrop ->
                            airdrop.airdropId
                        }
                    ) { airdrop ->
                        FeaturedAirdropCard(airdrop = airdrop)
                    }
                }
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
    TabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = selectedTabIndex,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        indicator = { tabPositions ->
            SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
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
                        modifier = Modifier.fillMaxWidth().padding(start = 0.dp),
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

@Composable
fun FeaturedAirdropCard(airdrop: Airdrop) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val cardWidth by remember {
        derivedStateOf {
            when {
                screenWidth < 600 -> screenWidth * 0.6f // Small screen, 60% width
                else -> screenWidth * 0.3f // Large screen, 30% width
            }
        }
    }

    val cardHeight by remember(cardWidth) {
        derivedStateOf {
            cardWidth * 9 / 16
        }
    }

    Card(
        modifier = Modifier
            .width(cardWidth.dp)
            .height(cardHeight.dp)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = airdrop.thumbnail,
            contentScale = ContentScale.FillBounds,
            contentDescription = "featureBanner"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AirdropHomeScreenPreview() {
    AirdropHomeScreen(
        uiState = AirdropHomeScreenUiState(
            featuredAirdrops = listOf(
                Airdrop(
                    "",
                    "Blast L2",
                    "",
                    "Blast",
                    "Blast yield comes from ETH staking and RWA protocols. The yield from these decentralized protocols is passed back to Blast users automatically. The default interest rate on other L2s is 0%. On Blast, it’s 4% for ETH and 5% for stablecoins.",
                    "",
                    listOf(""),
                    OfficialLinks(
                        documentation = "https://docs.blast.io/about-blast",
                        website = "https://blast.io/en",
                        whitePaper = "https://docs.blast.io/about-blast"
                    ),
                    "Blast is the only Ethereum L2 with native yield for ETH and stablecoins.",
                    "blast-l2",
                    SocialLinks(
                        discord = "https://x.com/blast",
                        x = "https://x.com/blast"
                    ),
                    "",
                    listOf(
                        "Create an account at Bitget.",
                        "Follow them on their Russian Twitter handle.",
                        "Join their Russian Telegram group and Telegram channel.",
                        "Join the Bitget Vkontakte page.",
                        "Submit your Bitget UID to @Anri_nap on Telegram.",
                        "The first 500 Russian participants will get 100 BGB each."
                    ),
                    listOf("ETH", "Arbitrum", "Blast", "Polygon", "Matic"),
                    "https://blog.bitfinex.com/wp-content/uploads/2024/03/BFX_ARB.png"
                )
            )
        )
    )
}
