package com.altsdrop.core.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun debouncedClick(onClick: () -> Unit): () -> Unit {
    var onClickUsed by remember { mutableStateOf(false) }

    return if (!onClickUsed) {
        {
            onClickUsed = true
            onClick()
        }
    } else {
        {}
    }
}