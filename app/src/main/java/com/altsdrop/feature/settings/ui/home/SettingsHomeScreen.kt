package com.altsdrop.feature.settings.ui.home

import android.content.Intent
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.altsdrop.R
import com.altsdrop.core.ui.component.FirebaseAsyncImage
import com.altsdrop.feature.settings.domain.model.Setting
import com.altsdrop.feature.settings.domain.model.User
import com.altsdrop.feature.settings.ui.component.AltsdropAlertDialog
import com.altsdrop.feature.settings.ui.component.AltsdropInputDialog
import com.altsdrop.feature.settings.ui.home.SettingsHomeScreenUiEvent.OnSettingClicked

@Composable
fun SettingsHomeRoute(
    viewModel: SettingsHomeViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isUserLoggedIn) {
        if (!uiState.isUserLoggedIn) {
            navigateToLogin()
        }
    }

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
                    Spacer(modifier = Modifier.height(8.dp))
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
                            LinkSetting(setting = setting)
                        }

                        is Setting.AlertDialog -> {
                            DialogSetting(
                                setting = setting,
                                onEvent = onEvent
                            )
                        }

                        is Setting.Intent -> {}
                        is Setting.None -> {}
                        is Setting.InputDialog -> {
                            InputDialogSetting(
                                setting = setting,
                                onEvent = onEvent
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            item {
                AppVersionInfo()
            }
        }
    }
}

@Composable
fun AppVersionInfo() {
    val context = LocalContext.current
    val packageInfo = remember {
        context.packageManager.getPackageInfo(context.packageName, 0)
    }

    val versionName = packageInfo.versionName ?: "N/A"
    val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageInfo.longVersionCode
    } else {
        @Suppress("DEPRECATION")
        packageInfo.versionCode.toLong()
    }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        text = "Version: $versionName ($versionCode)",
        style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.6f
            )
        ),
        textAlign = TextAlign.Center
    )
}


@Composable
fun InputDialogSetting(
    setting: Setting.InputDialog,
    onEvent: (SettingsHomeScreenUiEvent) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .clickable {
                showDialog = true
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
    }

    AltsdropInputDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = { input ->
            showDialog = false
            onEvent(
                SettingsHomeScreenUiEvent.OnInputDialogConfirm(
                    settingId = setting.id,
                    inputText = input
                )
            )
        },
        title = setting.title,
        text = setting.message,
        icon = ImageVector.vectorResource(id = setting.iconResId),
        confirmText = setting.confirmText,
        cancelText = setting.cancelText,
        placeholderText = setting.placeholderText,
        labelText = setting.labelText
    )
}

@Composable
fun DialogSetting(
    setting: Setting.AlertDialog,
    onEvent: (SettingsHomeScreenUiEvent) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .clickable {
                showDialog = true
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
    }

    AltsdropAlertDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = {
            showDialog = false
            onEvent(
                SettingsHomeScreenUiEvent.OnAlertDialogConfirm(
                    settingId = setting.id
                )
            )
        },
        title = setting.title,
        text = setting.message,
        icon = setting.icon,
        confirmText = setting.confirmText,
        cancelText = setting.cancelText
    )
}

@Composable
fun LinkSetting(setting: Setting.Link) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, setting.url.toUri())
                context.startActivity(intent)
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
                onEvent(OnSettingClicked(setting.id))
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
        FirebaseAsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            imageUrl = user.photoUrl,
            contentScale = ContentScale.FillBounds,
            contentDescription = "article image"
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
                .wrapContentHeight(),
        ) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1
            )

            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )
        }
    }
}