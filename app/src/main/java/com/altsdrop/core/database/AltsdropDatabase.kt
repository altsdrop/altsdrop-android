package com.altsdrop.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.altsdrop.core.database.dao.DappDao
import com.altsdrop.core.database.model.DappEntity
import com.altsdrop.core.database.util.TagsConverter

@Database(
    entities = [DappEntity::class],
    version = 1
)
@TypeConverters(TagsConverter::class)
abstract class AltsdropDatabase : RoomDatabase() {
    abstract fun dappDao(): DappDao
}