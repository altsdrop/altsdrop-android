package com.altsdrop.feature.dapp.ui.search

sealed class DappSearchScreenUiEvent {
    object NavigateBack : DappSearchScreenUiEvent()
    data class NavigateToDappWebsite(val url: String) : DappSearchScreenUiEvent()
}