package com.jenandsara.marvelapp.v2.data.datalocal.characterdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CharacterData")
data class CharacterEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var nome: String,
    @ColumnInfo
    var idAPI: Int,
    @ColumnInfo
    var descricao: String,
    @ColumnInfo
    var imgPath: String,
    @ColumnInfo
    var idUser: String,
    @ColumnInfo
    var isFavorite: Boolean = true
) {
    constructor(idAPI: Int): this(0, "", 0, "", "", "", true)
}