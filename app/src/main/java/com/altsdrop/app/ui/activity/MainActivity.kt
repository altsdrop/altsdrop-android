package com.altsdrop.app.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.altsdrop.app.navigation.MainNavHost
import com.altsdrop.app.ui.theme.AltsdropTheme
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                onNotificationPermissionGranted()
            } else {
                onNotificationPermissionDenied()
            }
        }

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
        checkAppUpdate()
        checkNotificationPermission()
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkPermissionStatus(
                isGranted = {
                    onNotificationPermissionGranted()
                },
                showRational = {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                },
                isNotGranted = {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            )
        } else {
            onNotificationPermissionGranted()
        }
    }

    private fun checkPermissionStatus(
        isGranted: () -> Unit,
        showRational: () -> Unit,
        isNotGranted: () -> Unit,
    ) {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                isGranted()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) -> {
                showRational()
            }

            else -> {
                isNotGranted()
            }
        }
    }

    private fun onNotificationPermissionGranted() {
        viewModel.enableNotificationSetting()
    }

    private fun onNotificationPermissionDenied() {
        Toast.makeText(this, "Notification Permission Denied", Toast.LENGTH_SHORT).show()
    }

    private fun checkAppUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        val activityResultLauncher =
            registerForActivityResult(StartIntentSenderForResult()) { result: ActivityResult ->
                // handle callback
                if (result.resultCode != RESULT_OK) {
                    Log.e("MainActivity", "Update flow failed! Result code: ${result.resultCode}")
                    // If the update is canceled or fails,
                    // you can request to start the update again.
                }
            }

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // an activity result launcher registered via registerForActivityResult
                    activityResultLauncher,
                    // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                    // flexible updates.
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                )
            }
        }
    }
}