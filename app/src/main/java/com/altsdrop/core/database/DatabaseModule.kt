package com.altsdrop.core.database

import android.content.Context
import androidx.room.Room
import com.altsdrop.core.database.dao.DappDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AltsdropDatabase {
        return Room.databaseBuilder(
            appContext,
            AltsdropDatabase::class.java,
            "altsdrop_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDappDao(database: AltsdropDatabase): DappDao {
        return database.dappDao()
    }
}