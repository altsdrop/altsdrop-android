package com.altsdrop.feature.dapp.ui

sealed class DappScreenUiEvent {
    data object NavigateToSearchDappScreen : DappScreenUiEvent()
    data class NavigateToDappWebsite(val url: String) : DappScreenUiEvent()
}