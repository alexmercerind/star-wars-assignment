package com.alexmercerind.starwars.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alexmercerind.starwars.model.Character
import com.alexmercerind.starwars.paging.CharactersPageSource
import com.alexmercerind.starwars.repository.StarWarsRepository
import kotlinx.coroutines.flow.Flow

class CharactersListViewModel(private val repository: StarWarsRepository = StarWarsRepository()) :
    ViewModel() {
    companion object {
        private const val PAGE_SIZE = 8
    }

    val items: Flow<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { CharactersPageSource(repository) }
    ).flow.cachedIn(viewModelScope)
}
