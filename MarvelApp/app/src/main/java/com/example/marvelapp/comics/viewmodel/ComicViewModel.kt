package com.example.marvelapp.comics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.marvelapp.comics.model.ComicsModel
import com.example.marvelapp.comics.repository.ComicRepository
import kotlinx.coroutines.Dispatchers

class ComicViewModel (val _repository: ComicRepository) : ViewModel() {

    private var _comicList: List<ComicsModel> = listOf()

    fun getComicList(id: Int) = liveData(Dispatchers.IO) {
        val response = _repository.getComicsById(id)
        _comicList = response.data.results
        emit(response.data.results)
    }

    class ComicsViewModelFactory(private val _repository: ComicRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ComicViewModel(_repository) as T
        }
    }
}