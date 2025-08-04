package com.altsdrop.feature.dapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.altsdrop.feature.dapp.ui.DappScreenUiAction.OnTabSelected
import kotlinx.coroutines.launch

@Composable
fun DappRoute(
    viewModel: DappScreenViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DappScreen(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DappScreen(
    uiState: DappScreenUiState,
    onAction: (DappScreenUiAction) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {

        TopAppBar(
            title = {
                Text(
                    text = "Discover dApps",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        SearchBar(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {}
                )
                .wrapContentSize()
                .padding(horizontal = 16.dp),
            inputField = {
                SearchBarDefaults.InputField(
                    query = "",
                    onQueryChange = {},
                    onSearch = {},
                    expanded = false,
                    onExpandedChange = {},
                    placeholder = { Text("Enter dApp name or Url") },
                    enabled = false,
                )
            },
            expanded = false,
            onExpandedChange = {},
            content = {}
        )

        TabsWithPager(
            uiState = uiState,
            onAction = onAction
        )
    }
}

@Composable
fun TabsWithPager(
    modifier: Modifier = Modifier,
    uiState: DappScreenUiState,
    onAction: (DappScreenUiAction) -> Unit = {}
) {
    val pagerState = rememberPagerState { uiState.tabs.size }
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        if (uiState.selectedTabIndex != pagerState.currentPage) {
            onAction(OnTabSelected(pagerState.currentPage))
        }
    }

    PrimaryScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = uiState.selectedTabIndex,
        minTabWidth = 0.dp,
        edgePadding = 8.dp,
        divider = {},
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                Modifier.tabIndicatorOffset(uiState.selectedTabIndex, matchContentSize = true),
                width = 16.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    ) {
        uiState.tabs.forEachIndexed { index, title ->
            NoRippleTab(
                selected = uiState.selectedTabIndex == index,
                onClick = {
                    scope.launch { pagerState.scrollToPage(index) }
                },
                content = {
                    Text(
                        text = title,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(
                                vertical = 8.dp,
                                horizontal = 8.dp
                            ),
                        color = if (uiState.selectedTabIndex == index) {
                            MaterialTheme.colorScheme.onBackground
                        } else {
                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        },
                        style = if (uiState.selectedTabIndex == index) {
                            MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        } else {
                            MaterialTheme.typography.titleMedium
                        }
                    )
                }
            )
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Page: ${uiState.tabs[page]}")
        }
    }
}

@Composable
fun NoRippleTab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier =
            modifier
                .selectable(
                    selected = selected,
                    onClick = onClick,
                    enabled = true,
                    role = Role.Tab,
                    interactionSource = interactionSource,
                    indication = null,
                )
                .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        content()
    }
}