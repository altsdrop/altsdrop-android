package com.altsdrop.feature.settings.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AltsdropInputDialog(
    showDialog: Boolean,
    icon: ImageVector,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    title: String,
    text: String,
    confirmText: String,
    cancelText: String,
    placeholderText: String,
    labelText: String,
) {
    if (showDialog) {
        var feedbackText by remember { mutableStateOf(TextFieldValue("")) }
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

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
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = text,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        modifier = Modifier
                            .height(120.dp)
                            .focusRequester(
                                focusRequester
                            ),
                        value = feedbackText,
                        onValueChange = { feedbackText = it },
                        textStyle = MaterialTheme.typography.bodySmall,
                        label = {
                            Text(
                                text = labelText,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Start
                            )
                        },
                        placeholder = {
                            Text(
                                text = placeholderText,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Start
                            )
                        },
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm(feedbackText.text)
                    }
                ) {
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