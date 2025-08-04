package com.altsdrop.feature.dapp.ui

sealed class DappScreenUiAction {
    data class OnTabSelected(val index: Int) : DappScreenUiAction()
}