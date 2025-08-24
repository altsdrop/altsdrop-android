package com.altsdrop.feature.dapp.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.altsdrop.R
import com.altsdrop.app.ui.theme.AltsdropTheme
import com.altsdrop.core.util.openCustomTab
import com.altsdrop.feature.dapp.ui.component.DappItem
import com.altsdrop.feature.dapp.ui.search.DappSearchScreenUiAction.OnBackClick
import com.altsdrop.feature.dapp.ui.search.DappSearchScreenUiAction.OnDappItemClick
import com.altsdrop.feature.dapp.ui.search.DappSearchScreenUiAction.OnSearchQueryChanged
import com.altsdrop.feature.dapp.ui.search.DappSearchScreenUiEvent.NavigateBack
import com.altsdrop.feature.dapp.ui.search.DappSearchScreenUiEvent.NavigateToDappWebsite
import kotlinx.coroutines.delay

@Composable
fun DappSearchRoute(
    viewModel: DappSearchScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                NavigateBack -> navigateBack()
                is NavigateToDappWebsite -> context.openCustomTab(event.url)
            }
        }
    }

    DappSearchScreen(
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
fun DappSearchScreen(
    uiState: DappSearchScreenUiState,
    onAction: (DappSearchScreenUiAction) -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier,
            uiState = uiState,
            onAction = onAction
        )

        AnimatedVisibility(
            visible = uiState.shouldShowNoResults
        ) {
            NoResults(
                modifier = Modifier.fillMaxSize().imePadding()
            )
        }

        AnimatedVisibility(
            visible = !uiState.shouldShowNoResults
        ) {
            SearchList(
                modifier = Modifier.weight(1f),
                uiState = uiState,
                onAction = onAction
            )
        }
    }
}

@Composable
private fun NoResults(
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(120.dp),
            imageVector = ImageVector.vectorResource(
                id = R.drawable.ic_no_results
            ),
            contentDescription = stringResource(
                id = R.string.content_description_back_icon
            ),
            tint = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.4f
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .align(Alignment.CenterHorizontally)
            ,
            text = stringResource(id = R.string.dapp_no_search_result_heading),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .align(Alignment.CenterHorizontally)
            ,
            text = stringResource(id = R.string.dapp_no_search_result),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.4f
            ),
        )
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier,
    uiState: DappSearchScreenUiState = DappSearchScreenUiState(),
    onAction: (DappSearchScreenUiAction) -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                shape = MaterialTheme.shapes.extraLarge
            )
            .padding(start = 4.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .clip(CircleShape),
            onClick = { onAction(OnBackClick) }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(
                    id = R.string.content_description_back_icon
                ),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        TextField(
            modifier = Modifier
                .weight(1f)
                .offset(x = (-10).dp)
                .focusRequester(focusRequester = focusRequester),
            value = uiState.query,
            onValueChange = {
                onAction(OnSearchQueryChanged(it))
            },
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.text_dapp_search_placeholder),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                disabledTextColor = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}

@Composable
private fun SearchList(
    modifier: Modifier = Modifier,
    uiState: DappSearchScreenUiState = DappSearchScreenUiState(),
    onAction: (DappSearchScreenUiAction) -> Unit = {},
) {
    LazyColumn(modifier = modifier) {
        items(
            count = uiState.dapps.size,
            key = { index -> uiState.dapps[index].id }
        ) { index ->
            DappItem(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    )
                    .clickable { onAction(OnDappItemClick(uiState.dapps[index].url)) },
                dappItem = uiState.dapps[index]
            )
        }
    }
}

@Preview
@Composable
fun DappSearchScreenPreview() {
    AltsdropTheme {
        DappSearchScreen(
            uiState = DappSearchScreenUiState(),
            onAction = {}
        )
    }
}

@Preview
@Composable
fun DappSearchScreenNoResultsPreview() {
    AltsdropTheme {
        DappSearchScreen(
            uiState = DappSearchScreenUiState(
                shouldShowNoResults = true
            ),
            onAction = {}
        )
    }
}