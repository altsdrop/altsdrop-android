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
    @ColumnInfo(name = "network")
    val network: String
)

fun DappEntity.toDomain() = Dapp(
    id = id,
    name = name,
    description = description,
    iconUrl = iconUrl,
    url = url,
    tags = tags,
    network = network
)

fun Dapp.toEntity() = DappEntity(
    id = id,
    name = name,
    description = description,
    iconUrl = iconUrl,
    url = url,
    tags = tags,
    network = network
)