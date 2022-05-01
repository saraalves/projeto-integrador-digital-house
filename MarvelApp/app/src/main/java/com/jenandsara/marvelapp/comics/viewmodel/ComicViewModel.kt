package com.jenandsara.marvelapp.comics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.jenandsara.marvelapp.data.model.comics.ComicsResponse
import com.jenandsara.marvelapp.domain.repository.ComicRepository
import kotlinx.coroutines.Dispatchers

class ComicViewModel (val _repository: ComicRepository) : ViewModel() {

    private var _totalPages: Int = 0
    private var _offset: Int = 0
    private var _count: Int = 0

    private var _comicList: List<ComicsResponse> = listOf()

    fun getComicList(id: Int) = liveData(Dispatchers.IO) {

        val response = _repository.getComicsById(id)
        _count = response.data.count
        _totalPages = if (response.data.total != 0) {
            response.data.total / _count
        } else {
            0
        }
        _comicList = response.data.results
        emit(response.data.results)
    }

    fun nextPage(id: Int) = liveData(Dispatchers.IO){
        if( _offset.plus(_count) <= _totalPages) {
            _offset = _offset.plus(_count)
            val response = _repository.getComicsById(id, _offset)
            emit(response.data.results)
        }
    }

    class ComicsViewModelFactory(private val _repository: ComicRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ComicViewModel(_repository) as T
        }
    }
}