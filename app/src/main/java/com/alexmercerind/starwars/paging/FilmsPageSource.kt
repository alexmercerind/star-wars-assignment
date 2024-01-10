package com.alexmercerind.starwars.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alexmercerind.starwars.model.Film
import com.alexmercerind.starwars.repository.StarWarsRepository
import com.alexmercerind.starwars.utils.Result

private const val INITIAL_FILM_INDEX = 0

class FilmsPageSource(
    filmURLs: List<String>,
    private val starWarsRepository: StarWarsRepository
) :
    PagingSource<Int, Film>() {

    // Extract film IDs from film URLs.
    private val filmIDs = filmURLs.map { it.split("/").mapNotNull { it.toIntOrNull() }.last() }

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        return (anchorPosition + 1).coerceIn(0, filmIDs.size - 1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val index = params.key ?: INITIAL_FILM_INDEX

        return when (val result = starWarsRepository.getFilm(filmIDs[index])) {
            is Result.Success -> LoadResult.Page(
                data = listOf(result.data!!),
                prevKey = if (index == 0) null else index - 1,
                nextKey = if (index == filmIDs.size - 1) null else index + 1
            )

            is Result.Error -> LoadResult.Error(result.error!!)
        }
    }
}
