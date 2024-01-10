package com.alexmercerind.starwars.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alexmercerind.starwars.model.Film
import com.alexmercerind.starwars.paging.FilmsPageSource
import com.alexmercerind.starwars.repository.StarWarsRepository
import kotlinx.coroutines.flow.Flow

class FilmsListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: StarWarsRepository = StarWarsRepository(application)

    companion object {
        private const val PAGE_SIZE = 1
    }

    fun getPagingDataForFilms(films: List<String>): Flow<PagingData<Film>> =
        Pager(config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { FilmsPageSource(films, repository) }).flow.cachedIn(
            viewModelScope
        )

}
