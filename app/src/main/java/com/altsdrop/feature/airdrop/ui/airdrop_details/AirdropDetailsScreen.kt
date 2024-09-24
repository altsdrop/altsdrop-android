package com.altsdrop.feature.airdrop.ui.airdrop_details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.altsdrop.core.ui.component.ErrorInfo
import com.altsdrop.feature.airdrop.domain.model.Airdrop
import com.altsdrop.feature.airdrop.domain.model.previewAirdrop

@Composable
fun AirdropHomeRoute(
    viewModel: AirdropDetailsViewModel = hiltViewModel(),
    slug: String
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(slug) {
        viewModel.getAirdropDetails(slug)
    }

    AirdropDetailsScreen(
        uiState = uiState
    )
}

@Composable
fun AirdropDetailsScreen(
    uiState: AirdropDetailsScreenUiState,
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        when (uiState) {
            is AirdropDetailsScreenUiState.Loading -> {
                CircularProgressIndicator()
            }

            is AirdropDetailsScreenUiState.Success -> {
                AirdropDetails(uiState.airdrop)
            }

            is AirdropDetailsScreenUiState.Error -> {
                ErrorInfo(
                    message = uiState.message,
                    onRetry = { /*TODO*/ }
                )
            }
        }
    }
}

@Composable
fun AirdropDetails(airdrop: Airdrop) {

}


@Preview(showBackground = true)
@Composable
fun AirdropHomeScreenPreview() {
    AirdropDetailsScreen(
        uiState = AirdropDetailsScreenUiState.Success(
            previewAirdrop
        )
    )
}
