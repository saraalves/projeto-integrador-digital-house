package com.jenandsara.marvelapp.v2.presentation.favoritos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.jenandsara.marvelapp.v2.domain.character.model.CharacterModel
import com.jenandsara.marvelapp.v2.data.datalocal.characterdatabase.CharacterEntity
import com.jenandsara.marvelapp.v2.data.datalocal.repository.CharacterLocalRepository
import kotlinx.coroutines.Dispatchers

class FavoriteViewModel (private val repository: CharacterLocalRepository): ViewModel() {

    fun setFavoriteCharacter(list: List<CharacterModel>, idUser: String) = liveData(Dispatchers.IO) {

        list.forEach {
            val isFavorite = repository.checkIfIsFavorite(it.id, idUser)
            it.isFavorite = isFavorite
            if(isFavorite) {
                addCharacter(it.nome, it.id, it.descricao, it.thumbnail!!.getImagePath(), idUser)
            }
        }
        emit(true)
    }

    fun getFavoriteCharacterLocal(idUser: String) = liveData(Dispatchers.IO) {

        val favorites = repository.getAllCharacters(idUser)
        val characters = mutableListOf<CharacterEntity>()

        favorites.forEach {
            it.isFavorite = true
            characters.add(it)
        }
        emit(characters)
    }

    fun addCharacter(nome: String, idAPI: Int, descricao: String, imgPath: String, idUser: String) = liveData(Dispatchers.IO) {
        repository.saveCharacter(CharacterEntity(0, nome,idAPI,descricao, imgPath, idUser))
        emit(true)
    }

    fun isFavorite(idAPI: Int, idUser: String) = liveData(Dispatchers.IO) {
        val result = repository.checkIfIsFavorite(idAPI, idUser)
        emit(result)
    }


    fun deleteCharacter(idAPI: Int) = liveData(Dispatchers.IO) {
        repository.deleteCharacter(idAPI)
        Log.d("TAG CHARACTER VIEWMODEL", "deleteCharacter()")
        emit(true)
    }


    class FavoritosViewModelFactory(private val characterLocalRepository: CharacterLocalRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FavoriteViewModel(characterLocalRepository) as T
        }
    }



}