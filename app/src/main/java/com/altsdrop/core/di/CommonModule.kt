package com.altsdrop.core.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.altsdrop.core.util.Resources
import com.altsdrop.core.util.ResourcesImpl
import com.altsdrop.core.util.ToastManager
import com.altsdrop.core.util.ToastManagerImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    @Singleton
    fun provideResources(@ApplicationContext context: Context): Resources {
        return ResourcesImpl(context)
    }

    @Provides
    @Singleton
    fun provideToastManager(@ApplicationContext context: Context): ToastManager {
        return ToastManagerImpl(context)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) =
        PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("settings_preferences")
        }
}