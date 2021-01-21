package com.jenandsara.marvelapp.datalocal

import android.app.Application
import android.content.SharedPreferences
import com.jenandsara.marvelapp.datalocal.dao.CharacterDao
import com.jenandsara.marvelapp.datalocal.entity.CharacterEntity

open class DataManager(
    application: Application,
    private var characterDao: CharacterDao
) {
    private val sharedPreferencesEdit = getSharedPrefs(application).edit()

    companion object {
        const val MARVEL_PREFERENCES = "marvel_preferences"
    }

    suspend fun adicionarCharacter(character: List<CharacterEntity>) = characterDao?.adicionarCharacter(character)
    suspend fun atualizarIsFavorite(int: Int, id_api: Int) = characterDao?.atualizarIsFavorite(int, id_api)
    suspend fun obterTodos(): List<CharacterEntity>? = characterDao?.obterTodos()
    suspend fun obterFavoritos(): List<CharacterEntity>? = characterDao?.obterFavoritos()

    suspend fun salvarCharacter(character: List<CharacterEntity>, isSuccess: (Boolean) -> Unit) {
        characterDao.adicionarCharacter(character)
    }

    fun getSharedPrefs(androidApplication: Application): SharedPreferences =
        androidApplication.getSharedPreferences(MARVEL_PREFERENCES, android.content.Context.MODE_PRIVATE)
}