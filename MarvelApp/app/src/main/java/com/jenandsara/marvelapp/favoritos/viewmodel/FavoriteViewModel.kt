package com.jenandsara.marvelapp.favoritos.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.character.repository.CharacterRepository
import com.jenandsara.marvelapp.character.viewmodel.CharactersViewModel
import com.jenandsara.marvelapp.favoritos.datalocal.characterdatabase.CharacterEntity
import com.jenandsara.marvelapp.favoritos.datalocal.repository.CharacterLocalRepository
import kotlinx.coroutines.Dispatchers

class FavoriteViewModel(private val _favoriteRepository: CharacterLocalRepository) : ViewModel() {

    private var _characterList: List<CharacterEntity> = listOf()
    private var _totalPages: Int = 0
    private var _offset: Int = 0
    private var _count: Int = 0

    fun getFavoriteCharacterLocal() = liveData(Dispatchers.IO) {
        val favorites = _favoriteRepository.getAllCharacters()
        val characters = mutableListOf<CharacterEntity>()

        favorites.forEach {
            val character = _favoriteRepository.getAllCharacters()[0]
            character.isFavorite = true
            characters.add(character)
            Log.d("TAG CHARACTER VIEWMODEL", "getFavoriteCharacter() - forEach: $character")
        }
        Log.d("TAG CHARACTER VIEWMODEL", "getFavoriteCharacter() $characters")
        emit(characters)
    }

    fun getFavoriteCharacter(list: List<CharacterModel>) = liveData(Dispatchers.IO) {
        val favorites = _favoriteRepository.getAllCharacters()
        val characters = mutableListOf<CharacterModel>()

        favorites.forEach {
            val character = list
//            character.isFavorite = true
//            characters.add(character)
            Log.d("TAG CHARACTER VIEWMODEL", "getFavoriteCharacter() - forEach: $character")
        }
        emit(characters)
    }

    fun deleteCharacter(idAPI: Int) = liveData(Dispatchers.IO) {
        _favoriteRepository.deleteCharacter(idAPI)
        Log.d("TAG CHARACTER VIEWMODEL", "deleteCharacter()")
        emit(true)
    }

    fun addCharacter(nome: String, idAPI: Int, descricao: String, imgPath: String) = liveData(Dispatchers.IO) {
        _favoriteRepository.saveCharacter(CharacterEntity(0, nome,idAPI,descricao, imgPath))
        Log.d("TAG CHARACTER VIEWMODEL", "addCharacter()")
        emit(true)
    }

    class FavoriteViewModelFactory(private val favoriteRepository: CharacterLocalRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FavoriteViewModel(favoriteRepository) as T
        }
    }

}