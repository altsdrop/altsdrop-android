package com.altsdrop.feature.login.ui.login

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.altsdrop.R
import com.altsdrop.app.ui.theme.AltsdropTheme
import com.altsdrop.feature.login.domain.model.SignInState
import com.altsdrop.feature.login.ui.component.GoogleLoginButton
import com.altsdrop.feature.login.ui.component.PageIndicator

@Composable
fun LoginRoute(
    navigateToHomeScreen: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        onLoginClick = viewModel::onSignInWithGoogleClick
    )

    val context = LocalContext.current

    LaunchedEffect(uiState.signInState) {
        if (uiState.signInState == SignInState.Success) {
            navigateToHomeScreen()
        }
        if (uiState.signInState is SignInState.Error) {
            Toast.makeText(
                context,
                (uiState.signInState as SignInState.Error).message,
                Toast.LENGTH_SHORT

            ).show()
        }
    }
}

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {}
) {

    val numberOfPages = 3
    val pagerState = rememberPagerState(pageCount = { numberOfPages })


    // HorizontalPager to update page based on user interaction
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        // Replace with your image or page content
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    model = when (page) {
                        0 -> R.drawable.ic_showcase_airdrops
                        1 -> R.drawable.ic_showcase_news
                        2 -> R.drawable.ic_showcase_notifications
                        else -> R.drawable.ic_showcase_notifications
                    },
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = when (page) {
                        0 -> "Altsdrop"
                        1 -> "News"
                        2 -> "Notifications"
                        else -> "Airdrops"
                    },
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = when (page) {
                        0 -> "Discover exclusive airdrops tailored just for you. Don't miss out on rewards waiting to be claimed!"
                        1 -> "Stay informed with the latest news updates. Everything you need to know, right at your fingertips."
                        2 -> "Get real-time alerts and updates. Stay connected and never miss important moments."
                        else -> "Discover exclusive airdrops tailored just for you."
                    },
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // PageIndicator linked to PagerState
                PageIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    numberOfPages = numberOfPages,
                    selectedPage = pagerState.currentPage,
                    defaultRadius = 8.dp,
                    selectedLength = 24.dp,
                    space = 8.dp,
                    animationDurationInMillis = 500
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                if (page == 2) {
                    GoogleLoginButton(onClick = onLoginClick)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
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
        LoginScreen()
    }
}