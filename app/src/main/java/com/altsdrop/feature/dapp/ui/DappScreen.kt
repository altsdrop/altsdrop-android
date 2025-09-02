package com.altsdrop.feature.dapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.altsdrop.R
import com.altsdrop.app.ui.theme.AltsdropTheme
import com.altsdrop.core.util.openCustomTab
import com.altsdrop.feature.dapp.domain.model.Dapp
import com.altsdrop.feature.dapp.ui.DappScreenUiAction.OnDappItemClick
import com.altsdrop.feature.dapp.ui.DappScreenUiAction.OnSearchBarClick
import com.altsdrop.feature.dapp.ui.DappScreenUiAction.OnSearchIconClick
import com.altsdrop.feature.dapp.ui.DappScreenUiAction.OnTabSelected
import com.altsdrop.feature.dapp.ui.DappScreenUiEvent.NavigateToDappWebsite
import com.altsdrop.feature.dapp.ui.DappScreenUiEvent.NavigateToSearchDappScreen
import com.altsdrop.feature.dapp.ui.component.DappItem
import kotlinx.coroutines.launch

@Composable
fun DappRoute(
    viewModel: DappScreenViewModel = hiltViewModel(),
    navigateToDappSearchScreen: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                NavigateToSearchDappScreen -> navigateToDappSearchScreen()
                is NavigateToDappWebsite -> context.openCustomTab(event.url)
            }
        }
    }

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
    val topBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(topBarScrollBehavior.nestedScrollConnection),
        topBar = {
            ExtendedTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                scrollBehavior = topBarScrollBehavior,
                onAction = onAction
            )
        },
        content = { padding ->
            TabsWithHorizontalPager(
                uiState = uiState,
                modifier = Modifier.padding(top = padding.calculateTopPadding()),
                onAction = onAction
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExtendedTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    onAction: (DappScreenUiAction) -> Unit = {}
) {
    val shouldShowSearchIcon by remember {
        derivedStateOf {
            scrollBehavior.state.collapsedFraction == 1f
        }
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.title_discover_d_apps),
                style = MaterialTheme.typography.titleLarge
                    .copy(fontWeight = FontWeight.Bold)
            )

            if (shouldShowSearchIcon) {
                IconButton(onClick = { onAction(OnSearchIconClick) }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(
                            id = R.string.content_description_search_icon
                        )
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }
        }

        TopAppBar(
            windowInsets = WindowInsets(0.dp),
            scrollBehavior = scrollBehavior,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            title = {
                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraLarge)
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainerHigh,
                            shape = MaterialTheme.shapes.extraLarge
                        )
                        .clickable { onAction(OnSearchBarClick) }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(
                            id = R.string.content_description_search_icon
                        ),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(id = R.string.text_dapp_search_placeholder),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            colors = TopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                scrolledContainerColor = MaterialTheme.colorScheme.background,
                navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                subtitleContentColor = MaterialTheme.colorScheme.onBackground,
            )
        )
    }
}

@Composable
fun Tabs(
    tabs: List<String>,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {}
) {
    PrimaryScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        minTabWidth = 0.dp,
        edgePadding = 8.dp,
        divider = {},
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                Modifier.tabIndicatorOffset(selectedTabIndex, matchContentSize = true),
                width = 16.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            NoRippleTab(
                selected = selectedTabIndex == index,
                onClick = { onClick(index) },
                content = {
                    Text(
                        text = title,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(
                                vertical = 8.dp,
                                horizontal = 8.dp
                            ),
                        color = if (selectedTabIndex == index) {
                            MaterialTheme.colorScheme.onBackground
                        } else {
                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        },
                        style = if (selectedTabIndex == index) {
                            MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        } else {
                            MaterialTheme.typography.titleMedium
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun TabsWithHorizontalPager(
    uiState: DappScreenUiState,
    modifier: Modifier = Modifier,
    onAction: (DappScreenUiAction) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val listState = List(uiState.tabs.size) {
        rememberLazyListState()
    }
    val pagerState = rememberPagerState { uiState.tabs.size }

    LaunchedEffect(pagerState.currentPage) {
        if (uiState.selectedTabIndex != pagerState.currentPage) {
            onAction(OnTabSelected(pagerState.currentPage))
        }
    }

    Column(modifier = modifier) {
        Tabs(
            tabs = uiState.tabs,
            selectedTabIndex = uiState.selectedTabIndex,
            onClick = { selectedIndex ->
                scope.launch { pagerState.scrollToPage(selectedIndex) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.Top
        ) { page ->
            LazyColumn(state = listState[page]) {
                items(
                    count = uiState.dAppsByTab[uiState.selectedTabName]?.size ?: 0,
                    key = {
                        uiState.dAppsByTab[uiState.selectedTabName]?.get(it)?.url ?: ""
                    }
                ) { index ->
                    if (index == 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    AnimatedVisibility(
                        visible = uiState.dAppsByTab[uiState.selectedTabName]?.get(index) != null
                    ) {
                        DappItem(
                            modifier = Modifier
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 16.dp
                                )
                                .clickable {
                                    onAction(
                                        OnDappItemClick(
                                            uiState
                                                .dAppsByTab[uiState.selectedTabName]
                                                ?.get(index)
                                                ?.url ?: ""
                                        )
                                    )
                                },
                            dappItem = uiState.dAppsByTab[uiState.selectedTabName]?.get(index)!!
                        )
                    }

                    if (index == 9) {
                        Spacer(modifier = Modifier.height(8.dp))
                    } else {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                            thickness = 0.5.dp
                        )
                    }
                }
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ExtendedTopAppBarPreview() {
    AltsdropTheme {
        ExtendedTopAppBar(
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun DappScreenPreview() {
    AltsdropTheme {
        DappScreen(
            uiState = DappScreenUiState()
        )
    }
}

@Preview
@Composable
fun DappItemPreview() {
    AltsdropTheme {
        DappItem(
            modifier = Modifier,
            dappItem = Dapp(
                id = "id",
                name = "Dapp Name",
                description = "Dapp Description",
                iconUrl = "",
                url = "",
                tags = listOf("DeFi", "Tag2"),
                chains = listOf("Solana", "Ethereum"),
                isArchived = false,
                isFeatured = false,
                isHighlyRated = false
            )
        )
    }
}
