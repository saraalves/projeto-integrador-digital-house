package com.jenandsara.marvelapp.character.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.character.repository.CharacterRepository
import com.jenandsara.marvelapp.favoritos.datalocal.characterdatabase.CharacterEntity
import com.jenandsara.marvelapp.favoritos.datalocal.repository.CharacterLocalRepository
import kotlinx.coroutines.Dispatchers

class CharactersViewModel(val _repository: CharacterRepository) : ViewModel() {

    private var _characterList: List<CharacterModel> = listOf()
    private var _characterBeforeSearch = listOf<CharacterModel>()
    private var _totalPages: Int = 0
    private var _offset: Int = 0
    private var _count: Int = 0

    private lateinit var characterLocalRepository: CharacterLocalRepository


    fun getList() = liveData(Dispatchers.IO) {
        val response = _repository.getCharacter()
        _count =response.data.count
        _totalPages = if (response.data.total != 0) {
            response.data.total / _count
        } else{
            0
        }
        _characterList = response.data.results
        emit(response.data.results)
    }

    fun searchByName(name: String?) = liveData(Dispatchers.IO) {
        _characterBeforeSearch = _characterList
        val response = _repository.getCharacterByName(name)
        emit(response.data.results)
    }

    fun searchByStartsWith(string: String?) = liveData(Dispatchers.IO) {
        _characterBeforeSearch = _characterList
        val response = _repository.getCharacterByStartsWith(string)
        emit(response.data.results)
    }

    fun initialList() = _characterBeforeSearch

    fun nextPage() = liveData(Dispatchers.IO){
        if( _offset.plus(_count) <= _totalPages) {
            _offset = _offset.plus(_count)
            val response = _repository.getCharacter(_offset)
            emit(response.data.results)
        }
    }

    fun getFavoriteCharacter() = liveData(Dispatchers.IO) {
        val favorites = characterLocalRepository.getAllCharacters()
        val characters = mutableListOf<CharacterModel>()

        favorites.forEach {
            val character = _repository.getCharacter().data.results[0]
            character.isFavorite = true
            characters.add(character)
        }

        emit(characters)
    }


    suspend fun createDatabase(characterList: List<CharacterEntity>) {

        characterList.forEach {
            characterLocalRepository.saveCharacter(it)
            Log.d("TAG CHARACTER VIEWMODEL", "createDatabase() - item: $it")
        }
    }

    fun addCharacter(idAPI: Int) = liveData(Dispatchers.IO) {
        characterLocalRepository.saveCharacter(CharacterEntity(idAPI))
        Log.d("TAG CHARACTER VIEWMODEL", "addCharacter()")
        emit(true)
    }

    fun isFavorite(idAPI: Int) = liveData(Dispatchers.IO) {
        val result = characterLocalRepository.checkIfIsFavorite(idAPI)
        Log.d("TAG CHARACTER VIEWMODEL", "isFavorite()")
        emit(result)
    }

    fun updateFavoriteCharacters(character: MutableList<CharacterModel>)= liveData(Dispatchers.IO) {
        val charactersToRemove = mutableListOf<CharacterModel>()
//        character.forEach {
//            val isFavorite = characterLocalRepository.checkIfIsFavorite(it.id)
//            if (!isFavorite) {
//                charactersToRemove.add(it)
//            }
//        }
        Log.d("TAG CHARACTER VIEWMODEL", "updateFavoriteCharacters()")
        emit(charactersToRemove)
    }

    fun deleteCharacter(idAPI: Int) = liveData(Dispatchers.IO) {
        characterLocalRepository.deleteCharacter(idAPI)
        Log.d("TAG CHARACTER VIEWMODEL", "deleteCharacter()")
        emit(true)
    }

    class CharactersViewModelFactory(private val _repository: CharacterRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharactersViewModel(_repository) as T
        }
    }
}

