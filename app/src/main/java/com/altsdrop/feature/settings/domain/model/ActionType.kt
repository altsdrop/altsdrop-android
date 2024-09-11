package com.altsdrop.feature.settings.domain.model

sealed class ActionType {
    data class Link(val url: String) : ActionType()
    data class IntentAction(val action: String) : ActionType() // For launching an intent
    data class Dialog(val dialogTitle: String, val dialogMessage: String) : ActionType() // To show a dialog
    data object Toggle : ActionType() // For on/off toggle settings
    data object None : ActionType() // No action, maybe just a label or static info
}
