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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val repository: StarWarsRepository
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 8
    }

    var pagingSource = CharactersPageSource(repository)
        private set

    fun resetPagingSource() {
        pagingSource = CharactersPageSource(repository)
    }

    val items: Flow<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { pagingSource }
    ).flow.cachedIn(viewModelScope)
}
