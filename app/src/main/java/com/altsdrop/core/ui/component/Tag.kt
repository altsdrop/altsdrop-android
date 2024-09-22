package com.altsdrop.core.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.altsdrop.app.ui.theme.AltsdropTheme

@Composable
fun TextChip(text: String) {
    Surface(
        modifier = Modifier,
        shape = RoundedCornerShape(
            16.dp
        ),               // Circular shape
        border = BorderStroke(
            0.5.dp,
            MaterialTheme.colorScheme.primary
        ), // Border with 2dp thickness and blue color
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 4.dp
                )
            ,  // Padding inside the chip
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Preview
@Composable
fun TextChipPreview() {
    AltsdropTheme {
        TextChip(text = "Chip")
    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TextChipDarkPreview() {
    AltsdropTheme {
        TextChip(text = "Chip")
    }
}