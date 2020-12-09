package com.example.marvelapp.character.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.marvelapp.character.model.CharacterModel
import com.example.marvelapp.character.repository.CharacterRepository
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

    fun searchByName(id: Int) = liveData(Dispatchers.IO) {
        _characterBeforeSearch = _characterList
        val response = _repository.getCharacterById(id)
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

    class CharactersViewModelFactory(private val _repository: CharacterRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharactersViewModel(_repository) as T
        }
    }
}