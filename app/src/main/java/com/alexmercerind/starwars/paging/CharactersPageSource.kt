package com.alexmercerind.starwars.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alexmercerind.starwars.model.Character
import com.alexmercerind.starwars.repository.StarWarsRepository
import com.alexmercerind.starwars.utils.Result

private const val INITIAL_PAGE = 1

class CharactersPageSource(private val starWarsRepository: StarWarsRepository) :
    PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val character = state.closestItemToPosition(anchorPosition) ?: return null
        return character.page + 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: INITIAL_PAGE

        return when (val result = starWarsRepository.getCharacters(page)) {
            is Result.Success -> LoadResult.Page(
                data = result.data!!,
                prevKey = if (page == INITIAL_PAGE) null else page - 1,
                nextKey = if (result.data.isEmpty()) null else page + 1
            )
            is Result.Error -> LoadResult.Error(result.error!!)
        }
    }
}
