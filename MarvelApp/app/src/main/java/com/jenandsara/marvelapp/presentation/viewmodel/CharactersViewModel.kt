package com.jenandsara.marvelapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.jenandsara.marvelapp.data.model.character.CharacterResponse
import com.jenandsara.marvelapp.domain.repository.CharacterRepository
import com.jenandsara.marvelapp.data.model.comics.ComicsResponse
import com.jenandsara.marvelapp.data.datasource.local.datalocal.characterdatabase.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlin.random.Random

class CharactersViewModel(private val _repository: CharacterRepository) : ViewModel() {

    private var _characterList: List<CharacterResponse> = listOf()
    private var _characterBeforeSearch = listOf<CharacterResponse>()
    private var _totalPages: Int = 0
    private var _offset: Int = 0
    private var _count: Int = 0

    fun getList() = liveData(Dispatchers.IO) {
        val response = _repository.getCharacter()
        _count = response.data.count
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
        Log.d("TAG CHARACTER VIEWMODEL", "nextPage()")
        if( _offset.plus(_count) <= _totalPages) {
            _offset = _offset.plus(_count)
            val response = _repository.getCharacter(_offset)
            emit(response.data.results)
        }
    }

    fun getRandomFavorite(list: MutableList<CharacterEntity>) = liveData(Dispatchers.IO){
        if(list.isNotEmpty()){
            val personagem = list[Random.nextInt(list.size - 1)]
            emit (personagem.idAPI)
        }
    }

    fun getRecomended(list: List<ComicsResponse>) = liveData(Dispatchers.IO){
        if(list.isNotEmpty()){
            val comic = list[Random.nextInt(list.size - 1)]
            val id = comic.id
            val response = _repository.getRecomended(id)
            emit(response.data.results)
        }
    }

    class CharactersViewModelFactory(private val _repository: CharacterRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharactersViewModel(_repository) as T
        }
    }
}

