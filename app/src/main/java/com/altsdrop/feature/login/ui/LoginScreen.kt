package com.altsdrop.feature.login.ui

import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.altsdrop.app.ui.theme.AltsdropTheme
import com.altsdrop.feature.login.ui.component.GoogleLoginButton

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LoginScreen(
        uiState = uiState,
       // onLoginClick = viewModel::onLoginClick
    )
}

@Composable
fun LoginScreen(
    uiState: LoginScreenUiState,
    //onLoginClick: () -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                buildAnnotatedString {
                    append("Welcome to ")

                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append("Altsdrop")
                    }
                },
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Join Now to Discover Top Airdrops and ICO Insights. Be the first to know about lucrative opportunities and market trends.",
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
            )

            Spacer(modifier = Modifier.height(16.dp))

            GoogleLoginButton()
        }
    }
}

@Preview(
    widthDp = 360,
    heightDp = 720,
)
@Composable
fun LoginScreenPreview() {
    AltsdropTheme {
        LoginScreen(uiState = LoginScreenUiState())
    }
}