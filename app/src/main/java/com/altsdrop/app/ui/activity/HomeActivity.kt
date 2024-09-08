package com.altsdrop.app.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.altsdrop.app.navigation.AltsDropNavHost
import com.altsdrop.app.ui.component.AltsdropNavigationBar
import com.altsdrop.app.ui.component.AltsdropNavigationItem
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

                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth(),
                            color = MaterialTheme.colorScheme.outline,
                            thickness = 0.2.dp
                        )

                        AltsdropNavigationBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                                .wrapContentHeight()
                                .selectableGroup(),
                        ) {
                            uiState.tabs.forEach { tab ->
                                AltsdropNavigationItem(
                                    modifier = Modifier
                                        .weight(1f),
                                    icon = {
                                        Icon(
                                            imageVector = if (tab.isSelected) {
                                                tab.selectedIcon
                                            } else {
                                                tab.unselectedIcon
                                            },
                                            contentDescription = ""
                                        )
                                    },
                                    onClick = {
                                        viewModel.onTabClicked(tab)
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
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