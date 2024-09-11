package com.altsdrop.feature.settings.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.altsdrop.R
import com.altsdrop.feature.settings.domain.model.Setting
import com.altsdrop.feature.settings.domain.model.User

@Composable
fun SettingsHomeRoute(
    viewModel: SettingsHomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsHomeScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun SettingsHomeScreen(
    uiState: SettingsHomeScreenUiState,
    onEvent: (SettingsHomeScreenUiEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.title_feature_settings),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
        ) {
            item {
                UserDetails(uiState.user)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            uiState.categories.forEach { settingsCategory ->
                item {
                    Text(
                        text = settingsCategory.categoryName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(settingsCategory.settings, key = { setting -> setting.id }) { setting ->
                    when (setting) {
                        is Setting.Toggle -> {
                            ToggleSetting(
                                setting = setting,
                                onEvent = onEvent
                            )
                        }

                        is Setting.Link -> {
                            LinkSetting(
                                setting = setting,
                                onEvent = onEvent
                            )
                        }

                        is Setting.Dialog -> {

                        }

                        is Setting.Intent -> {

                        }

                        is Setting.None -> {

                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun LinkSetting(setting: Setting.Link, onEvent: (SettingsHomeScreenUiEvent) -> Unit) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .clickable {
                onEvent(SettingsHomeScreenUiEvent.OnSettingClicked(setting.id))
            }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = setting.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.6f
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            tint = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.6f
            ),
            contentDescription = null
        )
    }
}

@Composable
fun ToggleSetting(setting: Setting.Toggle, onEvent: (SettingsHomeScreenUiEvent) -> Unit) {
    Row(
        modifier = Modifier
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = setting.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.6f
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Switch(
            checked = setting.toggleState,
            onCheckedChange = {
                onEvent(SettingsHomeScreenUiEvent.OnSettingClicked(setting.id))
            },
            thumbContent = {
                if (setting.toggleState) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            }
        )
    }
}

@Composable
fun UserDetails(user: User) {
    Row {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            model = user.photoUrl,
            contentScale = ContentScale.FillBounds,
            contentDescription = "article image"
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = user.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}