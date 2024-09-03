package com.altsdrop.app.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.altsdrop.app.navigation.AltsDropNavHost
import com.altsdrop.app.ui.theme.AltsdropTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            AltsdropTheme {
                val navHostController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            modifier = Modifier

                        ) {
                            uiState.tabs.forEach { tab ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            tab.icon,
                                            contentDescription = ""
                                        )
                                    },
                                    alwaysShowLabel = false,
                                    selected = tab.isSelected,
                                    onClick = {
                                        viewModel.onTabClicked(tab)
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    println(innerPadding)
                    AltsDropNavHost(
                        modifier = Modifier.padding(innerPadding),
                        navHostController = navHostController,
                        feature = uiState.tabs.first { it.isSelected }.feature
                    )
                }
            }
        }
    }
}