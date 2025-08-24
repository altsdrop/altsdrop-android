package com.altsdrop.feature.dapp.ui

sealed class DappScreenUiAction {
    data class OnTabSelected(val index: Int) : DappScreenUiAction()
    data object OnSearchIconClick : DappScreenUiAction()
    data object OnSearchBarClick : DappScreenUiAction()
    data class OnDappItemClick(val url: String) : DappScreenUiAction()
}