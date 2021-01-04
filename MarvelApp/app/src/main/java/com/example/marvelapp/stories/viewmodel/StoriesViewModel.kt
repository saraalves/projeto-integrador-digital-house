package com.example.marvelapp.stories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.marvelapp.stories.repository.StoriesRepository
import com.example.marvelapp.stories.model.StoriesModel
import kotlinx.coroutines.Dispatchers

class StoriesViewModel (val _repository: StoriesRepository) : ViewModel() {

    private var _storiesList: List<StoriesModel> = listOf()

    fun getStoriesList(id: Int) = liveData(Dispatchers.IO) {
        val response = _repository.getStoriesById(id)
        _storiesList = response.data.results
        emit(response.data.results)
    }

    class StoriesViewModelFactory(private val _repository: StoriesRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return StoriesViewModel(_repository) as T
        }
    }
}