package com.altsdrop.feature.airdrop.ui.airdrop_details

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.altsdrop.app.ui.theme.AltsdropTheme
import com.altsdrop.core.ui.component.getHtmlText
import com.altsdrop.core.util.openCustomTab
import com.altsdrop.core.util.toRgba
import com.altsdrop.feature.airdrop.domain.model.Step

@Composable
fun StepsDropdown(steps: List<Step>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        steps.forEachIndexed { index, step ->
            StepDropdownItem(step = step)

            if (index < steps.lastIndex) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun StepDropdownItem(step: Step) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val backgroundColor = MaterialTheme.colorScheme.background.toRgba()
    val bodyColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f).toRgba()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        onClick = { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row {
                Text(
                    text = step.title,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    imageVector = if (expanded)
                        Icons.Default.KeyboardArrowUp
                    else
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            AnimatedVisibility(visible = expanded) {
                AndroidView(
                    factory = { ctx ->
                        WebView(ctx).apply {
                            webViewClient = object : WebViewClient() {
                                override fun shouldOverrideUrlLoading(
                                    view: WebView,
                                    request: WebResourceRequest
                                ): Boolean {
                                    context.openCustomTab(request.url.toString())
                                    return true
                                }
                            }
                            loadDataWithBaseURL(
                                null,
                                getHtmlText(step.content, backgroundColor, bodyColor),
                                "text/html",
                                "UTF-8",
                                null
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun StepsDropDownPreview() {
    val steps = listOf(
        Step(
            title = "Step 1: Register on the website",
            content = "Content for Step 1"
        ),
        Step(
            title = "Step 2: Complete the registration form",
            content = "Content for Step 2"
        ),
        Step(
            title = "Step 3: Submit the application",
            content = "Content for Step 3"
        )
    )

    AltsdropTheme {
        StepsDropdown(steps = steps)
    }
}