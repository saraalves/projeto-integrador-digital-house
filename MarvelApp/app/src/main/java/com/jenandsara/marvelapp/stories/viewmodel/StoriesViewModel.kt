package com.jenandsara.marvelapp.stories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.jenandsara.marvelapp.stories.model.StoriesModel
import com.jenandsara.marvelapp.stories.repository.StoriesRepository
import kotlinx.coroutines.Dispatchers


class StoriesViewModel (val _repository: StoriesRepository) : ViewModel() {

    private var _totalPages: Int = 0
    private var _offset: Int = 0
    private var _count: Int = 0

    private var _storiesList: List<StoriesModel> = listOf()

    fun getStoriesList(id: Int) = liveData(Dispatchers.IO) {
        val response = _repository.getStoriesById(id)
        _count = response.data.count
        _totalPages = if (response.data.total != 0) {
            response.data.total / _count
        } else{
            0
        }
        _storiesList = response.data.results
        emit(response.data.results)
    }

    fun nextPage(id: Int) = liveData(Dispatchers.IO){
        if( _offset.plus(_count) <= _totalPages) {
            _offset = _offset.plus(_count)
            val response = _repository.getStoriesById(id, _offset)
            emit(response.data.results)
        }
    }

    class StoriesViewModelFactory(private val _repository: StoriesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return StoriesViewModel(_repository) as T
        }
    }
}