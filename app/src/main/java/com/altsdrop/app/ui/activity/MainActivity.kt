package com.altsdrop.app.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.altsdrop.app.navigation.MainNavHost
import com.altsdrop.app.ui.theme.AltsdropTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            AltsdropTheme {
                val navHostController = rememberNavController()
                uiState.startDestination?.let { startDestination ->
                    MainNavHost(
                        modifier = Modifier.fillMaxSize(),
                        navHostController = navHostController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}