package com.altsdrop.core.database.util

import androidx.room.TypeConverter

class TagsConverter {
    @TypeConverter
    fun fromTagsList(tags: List<String>?): String {
        return tags?.joinToString(separator = ",") ?: ""
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