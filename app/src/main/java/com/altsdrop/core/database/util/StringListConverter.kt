package com.altsdrop.core.database.util

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromTagsList(data: List<String>?): String {
        return data?.joinToString(separator = ",") ?: ""
    }

    @TypeConverter
    fun toTagsList(data: String?): List<String> {
        return data
            ?.split(",")
            ?.map { it.trim() }
            ?.filter { it.isNotEmpty() }
            ?: emptyList()
    }
}