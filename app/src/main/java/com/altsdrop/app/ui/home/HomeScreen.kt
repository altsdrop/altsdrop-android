package com.altsdrop.app.ui.home

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.altsdrop.app.navigation.HomeScreenNavHost
import com.altsdrop.app.ui.component.AltsdropNavigationBar
import com.altsdrop.app.ui.component.AltsdropNavigationItem

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onTabClicked = viewModel::onTabClicked
    )
}

@Composable
fun HomeScreen(
    uiState: HomeScreenUiState,
    onTabClicked: (Tab) -> Unit
) {
    val navHostController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
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
                                imageVector = ImageVector.vectorResource(
                                    id = if (tab.isSelected) {
                                        tab.selectedIcon
                                    } else {
                                        tab.unselectedIcon
                                    }
                                ),
                                contentDescription = ""
                            )
                        },
                        onClick = { onTabClicked(tab) }
                    )
                }
            }
        }
    ) { innerPadding ->
        HomeScreenNavHost(
            modifier = Modifier.padding(innerPadding),
            navHostController = navHostController,
            startDestination = uiState.tabs.first { it.isSelected }.homeScreenTab
        )
    }
}