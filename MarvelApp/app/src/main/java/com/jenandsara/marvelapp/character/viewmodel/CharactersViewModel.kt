package com.jenandsara.marvelapp.character.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.jenandsara.marvelapp.character.model.CharacterModel
import com.jenandsara.marvelapp.character.repository.CharacterRepository
import com.jenandsara.marvelapp.datalocal.entity.CharacterEntity
import kotlinx.coroutines.Dispatchers

class CharactersViewModel(val _repository: CharacterRepository) : ViewModel() {

    private var _characterList: List<CharacterModel> = listOf()
    private var _characterBeforeSearch = listOf<CharacterModel>()
    private var _totalPages: Int = 0
    private var _offset: Int = 0
    private var _count: Int = 0


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

    //    fun adiconarCharacter(name: String, id_api: Int, description: String, imgUrl: String, isFavorite: Int) = liveData(Dispatchers.IO) {
//
//        val character = CharacterEntity(0, name, id_api, description, imgUrl, isFavorite)
//
//        characterRepository.adicionarCharacter(character)
//        emit(character)
//    }

    fun adiconarCharacter(characterEntity: CharacterEntity) = liveData(Dispatchers.IO) {
        _repository.adicionarCharacter(characterEntity)
        emit(true)
    }

    fun obterFavoritos() = liveData(Dispatchers.IO) {
        emit(_repository.obterFavoritos())
    }

    fun obterTodos() = liveData(Dispatchers.IO) {
        emit(_repository.obterTodos())
    }

    fun atualizarIsFavorite(int: Int, id_api: Int) = liveData(Dispatchers.IO) {
        _repository.atualizarIsFavorite(int, id_api)
        emit(true)
    }

    class CharactersViewModelFactory(private val _repository: CharacterRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharactersViewModel(_repository) as T
        }
    }
}