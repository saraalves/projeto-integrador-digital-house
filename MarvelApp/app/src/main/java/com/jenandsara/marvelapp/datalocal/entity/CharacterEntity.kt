package com.jenandsara.marvelapp.datalocal.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LocalData")

data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var id_api: Int,
    @ColumnInfo
    var description: String,
    @ColumnInfo
    var imgUrl: String,
    @ColumnInfo
    var isFavorite: Int
)

