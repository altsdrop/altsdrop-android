package com.altsdrop.app

import android.app.Application
import com.altsdrop.BuildConfig
import com.altsdrop.R
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AltsdropApplication : Application() {

    @Inject
    lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Firebase.appCheck.installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance()
            )
        } else {
            Firebase.appCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance()
            )
        }
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = true
        setUpRemoteConfig()
    }

    private fun setUpRemoteConfig() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }

        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        firebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    FirebaseCrashlytics.getInstance().log(
                        "Remote config fetch and activate successful"
                    )
                } else {
                    FirebaseCrashlytics.getInstance().log(
                        "Remote config fetch failed: ${task.exception}"
                    )
                }
            }
    }
}