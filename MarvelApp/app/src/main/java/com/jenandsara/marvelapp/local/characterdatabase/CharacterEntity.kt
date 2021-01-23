package com.jenandsara.marvelapp.local.characterdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CharacterData")
data class CharacterEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo
    var nome: String,
    @ColumnInfo
    var idAPI: Int,
    @ColumnInfo
    var descricao: String,
    @ColumnInfo
    var imgPath: String
)