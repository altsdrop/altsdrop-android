package com.altsdrop.core.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.altsdrop.app.ui.theme.AltsdropTheme

@Composable
fun TextChip(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
    onClick: () -> Unit = {},
    borderColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    icon: ImageVector? = null,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(0.5.dp, borderColor),
    ) {
        Box(
            modifier = Modifier
                .clickable { onClick() }
                .background(color = backgroundColor)
                .padding(contentPadding),  // Padding inside the chip
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (icon != null) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = icon,
                        contentDescription = null,
                        tint = textColor.copy(alpha = 0.6f)
                    )
                }

                Text(
                    text = text,
                    style = textStyle,
                    color = textColor
                )
            }

        }
    }
}

@Preview
@Composable
fun TextChipPreview() {
    AltsdropTheme {
        TextChip(
            text = "Chip",
            icon = Icons.AutoMirrored.Filled.ArrowBack
        )
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