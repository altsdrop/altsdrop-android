package com.altsdrop.feature.settings.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AltsdropAlertDialog(
    showDialog: Boolean,
    icon: ImageVector,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    text: String,
    confirmText: String,
    cancelText: String
) {
    if (showDialog) {
        AlertDialog(
            icon = {
                Icon(imageVector = icon, contentDescription = null)
            },
            onDismissRequest = {
                onDismiss()
            },
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            },
            text = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
            },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text(confirmText)
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(cancelText)
                }
            }
        )
    }
}