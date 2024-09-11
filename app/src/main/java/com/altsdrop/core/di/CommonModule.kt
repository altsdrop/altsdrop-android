package com.altsdrop.core.di

import android.content.Context
import com.altsdrop.core.util.Resources
import com.altsdrop.core.util.ResourcesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object CommonModule {

    @Provides
    fun provideResources(@ApplicationContext context: Context): Resources {
        return ResourcesImpl(context)
    }
}