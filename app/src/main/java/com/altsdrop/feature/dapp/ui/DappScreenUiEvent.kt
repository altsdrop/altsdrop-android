package com.altsdrop.feature.dapp.ui

sealed class DappScreenUiEvent {
    data object NavigateToSearchDappScreen : DappScreenUiEvent()
}