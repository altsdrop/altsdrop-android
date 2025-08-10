package com.altsdrop.feature.dapp.ui.search

sealed class DappSearchScreenUiAction {
    data class OnSearchQueryChanged(val query: String) : DappSearchScreenUiAction()
    object OnBackClick : DappSearchScreenUiAction()
}