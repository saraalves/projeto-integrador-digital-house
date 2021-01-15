package com.jenandsara.marvelapp.datalocal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.jenandsara.marvelapp.datalocal.entity.CharacterEntity
import com.jenandsara.marvelapp.datalocal.repository.LocalCharacterRepository
import kotlinx.coroutines.Dispatchers

class LocalCharacterViewModel(private val characterRepository: LocalCharacterRepository): ViewModel() {

    fun adiconarChracter(name: String, id_api: Int, description: String, imgUrl: String, isFavorite: Int) = liveData(Dispatchers.IO) {

        val character = CharacterEntity(0, name, id_api, description, imgUrl, isFavorite)

        characterRepository.adicionarCharacter(character)
        emit(character)
    }

    fun obterFavoritos() = liveData(Dispatchers.IO) {
        emit(characterRepository.obterFavoritos())
    }

    fun obterTodos() = liveData(Dispatchers.IO) {
        emit(characterRepository.obterTodos())
    }

    fun atualizarIsFavorite(int: Int, id_api: Int) = liveData(Dispatchers.IO) {
        characterRepository.atualizarIsFavorite(int, id_api)
        emit(true)
    }

    class LocalCharacterViewModelFactory(private val characterRepository: LocalCharacterRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LocalCharacterViewModel(characterRepository) as T
        }
    }
}