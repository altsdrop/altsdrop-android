package com.altsdrop.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.altsdrop.feature.dapp.domain.model.Dapp

@Entity(tableName = "dapps")
data class DappEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "icon_url")
    val iconUrl: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "tags")
    val tags: List<String>,
    @ColumnInfo(name = "chains")
    val chains: List<String>,
    @ColumnInfo(name = "isArchived")
    val isArchived: Boolean,
    @ColumnInfo(name = "isFeatured")
    val isFeatured: Boolean,
    @ColumnInfo(name = "isHighlyRated")
    val isHighlyRated: Boolean
)

fun DappEntity.toDomain() = Dapp(
    id = id,
    name = name,
    description = description,
    iconUrl = iconUrl,
    url = url,
    tags = tags,
    chains = chains,
    isArchived = isArchived,
    isFeatured = isFeatured,
    isHighlyRated = isHighlyRated
)

fun Dapp.toEntity() = DappEntity(
    id = id,
    name = name,
    description = description,
    iconUrl = iconUrl,
    url = url,
    tags = tags,
    chains = chains,
    isArchived = isArchived,
    isFeatured = isFeatured,
    isHighlyRated = isHighlyRated
)