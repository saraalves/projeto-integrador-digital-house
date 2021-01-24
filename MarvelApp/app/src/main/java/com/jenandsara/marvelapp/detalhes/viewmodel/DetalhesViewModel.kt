package com.jenandsara.marvelapp.detalhes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.jenandsara.marvelapp.character.repository.CharacterRepository
import com.jenandsara.marvelapp.favoritos.datalocal.repository.CharacterLocalRepository
import kotlinx.coroutines.Dispatchers

class DetalhesViewModel(
    private val _characterRepository: CharacterRepository,
    private val _characterLocalRepository: CharacterLocalRepository
) : ViewModel() {

    fun getCharacter(characterId: Int) = liveData(Dispatchers.IO) {
        val response = _characterRepository.getCharacter(characterId)
        val character = response.data.results[0]
        character.isFavorite = _characterLocalRepository.checkIfIsFavorite(character.id)
        emit(character)
    }

    class DetalherViewModelFactory(
        private val _characterRepository: CharacterRepository,
        private val _characterLocalRepository: CharacterLocalRepository
    ) :ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetalhesViewModel(_characterRepository, _characterLocalRepository) as T
        }

    }
}