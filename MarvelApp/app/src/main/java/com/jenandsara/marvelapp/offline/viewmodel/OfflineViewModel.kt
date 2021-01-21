package com.jenandsara.marvelapp.offline.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.jenandsara.marvelapp.offline.entity.CharacterEntity
import com.jenandsara.marvelapp.offline.repository.OfflineRepository
import kotlinx.coroutines.Dispatchers

class OfflineViewModel(private val repository: OfflineRepository): ViewModel() {

    fun adicionarCharacter(nome: String, idAPI: Int, descricao: String, imgPath: String) = liveData(Dispatchers.IO){
        val character = CharacterEntity(0, nome,idAPI,descricao, imgPath)
        repository.adicionarCharacter(character)
        emit(character)
    }

    fun getAllCharacters() = liveData(Dispatchers.IO){
        emit(repository.getAllCharacters())
    }

    class OfflineViewModelFactory(private val repository: OfflineRepository) :
            ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return OfflineViewModel(repository) as T
        }
    }
}