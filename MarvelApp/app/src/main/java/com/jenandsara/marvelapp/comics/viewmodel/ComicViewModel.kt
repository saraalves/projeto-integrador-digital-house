package com.jenandsara.marvelapp.comics.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.jenandsara.marvelapp.comics.model.ComicsModel
import com.jenandsara.marvelapp.comics.repository.ComicRepository
import kotlinx.coroutines.Dispatchers

class ComicViewModel (val _repository: ComicRepository) : ViewModel() {

    private var _comicList: List<ComicsModel> = listOf()
    private var _totalPages: Int = 0
    private var _offset: Int = 0
    private var _count: Int = 0

    fun getComicList(id: Int) = liveData(Dispatchers.IO) {
        val response = _repository.getComicsById(id)
        _comicList = response.data.results
        emit(response.data.results)
    }

    fun nextPage() = liveData(Dispatchers.IO){
        Log.d("TAG CHARACTER VIEWMODEL", "nextPage()")
        if( _offset.plus(_count) <= _totalPages) {
            _offset = _offset.plus(_count)
            val response = _repository.getComicsById(_offset)
            emit(response.data.results)
        }
    }

    class ComicsViewModelFactory(private val _repository: ComicRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ComicViewModel(_repository) as T
        }
    }
}