package com.altsdrop.feature.dapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.altsdrop.R
import com.altsdrop.app.ui.theme.AltsdropTheme
import com.altsdrop.core.ui.component.TextChip
import com.altsdrop.feature.dapp.ui.DappScreenUiAction.OnSearchBarClick
import com.altsdrop.feature.dapp.ui.DappScreenUiAction.OnSearchIconClick
import com.altsdrop.feature.dapp.ui.DappScreenUiAction.OnTabSelected
import kotlinx.coroutines.launch

@Composable
fun DappRoute(
    viewModel: DappScreenViewModel = hiltViewModel(),
    navigateToDappSearchScreen: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                DappScreenUiEvent.NavigateToSearchDappScreen -> {
                    navigateToDappSearchScreen()
                }
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
                .weight(1f)
        ) { page ->
            LazyColumn(state = listState[page]) {
                items(
                    count = 10,
                    key = { index -> "item_$index" }
                ) { index ->
                    if (index == 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    DappItem(
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 16.dp
                        )
                    )

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
private fun DappItem(
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val url = "https://m3.material.io/static/assets/m3-favicon.svg"

            AsyncImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(4.dp)
                    .aspectRatio(1f / 1f)
                    .clip(RoundedCornerShape(12.dp)), // Change dp for more/less rounding
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .diskCachePolicy(CachePolicy.ENABLED)    // Enable disk cache
                    .memoryCachePolicy(CachePolicy.ENABLED)  // Enable memory cache
                    .apply {
                        if (url.endsWith(".svg", ignoreCase = true)) {
                            decoderFactory(SvgDecoder.Factory())
                        }
                    }
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.content_description_dapp),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier,
                    text = "Uniswap",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(4.dp))

                FlowRow(
                    maxLines = 1,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TextChip(
                        text = "DEX",
                        contentPadding = PaddingValues(
                            horizontal = 12.dp,
                            vertical = 2.dp
                        ),
                        textStyle = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 8.sp
                        ),
                        borderColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        textColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    TextChip(
                        text = "Exchange",
                        contentPadding = PaddingValues(
                            horizontal = 12.dp,
                            vertical = 2.dp
                        ),
                        textStyle = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 8.sp
                        ),
                        borderColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                        textColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier,
            text = "A decentralized exchange (DEX) built on Ethereum that allows users to swap ERC-20 tokens directly from their wallets without relying on a centralized intermediary, using automated market maker (AMM) smart contracts to determine pricing and liquidity.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.5f
            ),
            maxLines = 2
        )
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
            modifier = Modifier
        )
    }
}
