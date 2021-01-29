package com.jenandsara.marvelapp.favoritos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.favoritos.datalocal.characterdatabase.CharacterEntity
import com.jenandsara.marvelapp.favoritos.datalocal.repository.CharacterLocalRepository
import kotlinx.coroutines.Dispatchers

class FavoriteViewModel (private val repository: CharacterLocalRepository): ViewModel() {

    fun setFavoriteCharacter(list: List<CharacterModel>) = liveData(Dispatchers.IO) {

        list.forEach {
            val isFavorite = repository.checkIfIsFavorite(it.id)
            it.isFavorite = isFavorite
            if(isFavorite) {
                addCharacter(it.nome, it.id, it.descricao, it.thumbnail!!.getImagePath())
            }
        }
        emit(true)
    }

    fun getFavoriteCharacterLocal() = liveData(Dispatchers.IO) {

        val favorites = repository.getAllCharacters()
        val characters = mutableListOf<CharacterEntity>()

        favorites.forEach {
            it.isFavorite = true
            characters.add(it)
        }
        emit(characters)
    }

    fun addCharacter(nome: String, idAPI: Int, descricao: String, imgPath: String) = liveData(Dispatchers.IO) {
        repository.saveCharacter(CharacterEntity(0, nome,idAPI,descricao, imgPath))
        emit(true)
    }

    fun isFavorite(idAPI: Int) = liveData(Dispatchers.IO) {
        val result = repository.checkIfIsFavorite(idAPI)
        emit(result)
    }


    fun deleteCharacter(idAPI: Int) = liveData(Dispatchers.IO) {
        repository.deleteCharacter(idAPI)
        emit(true)
    }

    class FavoriteViewModelFactory(private val favoriteRepository: CharacterLocalRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FavoriteViewModel(favoriteRepository) as T
        }
    }
}