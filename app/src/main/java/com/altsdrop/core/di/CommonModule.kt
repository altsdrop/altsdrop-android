package com.altsdrop.core.di

import android.content.Context
import com.altsdrop.core.util.Resources
import com.altsdrop.core.util.ResourcesImpl
import com.altsdrop.core.util.ToastManager
import com.altsdrop.core.util.ToastManagerImpl
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
}