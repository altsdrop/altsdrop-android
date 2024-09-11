package com.altsdrop.feature.settings.domain.model

sealed class Setting(
    open val id: String,
    open val name: String,
    open val description: String? = null,
    open val isEnabled: Boolean = true // Whether the setting is enabled or disabled
) {
    data class Toggle(
        val toggleState: Boolean,
        override val id: String,
        override val name: String,
        override val description: String? = null,
        override val isEnabled: Boolean = true
    ) : Setting(id, name, description, isEnabled)

    data class Link(
        val url: String,
        override val id: String,
        override val name: String,
        override val description: String? = null,
        override val isEnabled: Boolean = true
    ) : Setting(id, name, description, isEnabled)

    data class Intent(
        val intentAction: String,
        override val id: String,
        override val name: String,
        override val description: String? = null,
        override val isEnabled: Boolean = true
    ) : Setting(id, name, description, isEnabled)

    data class Dialog(
        val dialogTitle: String,
        val dialogMessage: String,
        override val id: String,
        override val name: String,
        override val description: String? = null,
        override val isEnabled: Boolean = true
    ) : Setting(id, name, description, isEnabled)

    data class None(
        override val id: String,
        override val name: String,
        override val description: String? = null,
        override val isEnabled: Boolean = true
    ) : Setting(id, name, description, isEnabled)
}