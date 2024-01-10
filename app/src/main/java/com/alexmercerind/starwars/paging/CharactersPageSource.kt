package com.alexmercerind.starwars.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alexmercerind.starwars.model.Character
import com.alexmercerind.starwars.repository.StarWarsRepository
import com.alexmercerind.starwars.utils.Constants
import com.alexmercerind.starwars.utils.Result

private const val INITIAL_PAGE = 1

class CharactersPageSource(private val starWarsRepository: StarWarsRepository) :
    PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val character = state.closestItemToPosition(anchorPosition) ?: return null
        return character.page + 1
    }

    // Filter: Name
    var name = ""

    // Filter: Gender
    val genders = mutableSetOf(
        Constants.GENDER_FEMALE,
        Constants.GENDER_MALE,
        Constants.GENDER_OTHERS
    )

    fun addGender(value: String) {
        genders.add(value)
    }

    fun removeGender(value: String) {
        genders.remove(value)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: INITIAL_PAGE

        return when (val result = starWarsRepository.getCharacters(page)) {
            is Result.Success -> {

                // Apply filters.
                val data = result.data!!
                    .filter { it.name.contains(name, ignoreCase = true) }
                    .filter {
                        (genders.contains(Constants.GENDER_MALE) && it.gender == Constants.GENDER_MALE) ||
                                (genders.contains(Constants.GENDER_FEMALE) && it.gender == Constants.GENDER_FEMALE) ||
                                (genders.contains(Constants.GENDER_OTHERS) && it.gender != Constants.GENDER_MALE && it.gender != Constants.GENDER_FEMALE)
                    }

                LoadResult.Page(
                    data = data,
                    prevKey = if (page == INITIAL_PAGE) null else page - 1,
                    nextKey = if (data.isEmpty()) null else page + 1
                )
            }

            is Result.Error -> LoadResult.Error(result.error!!)
        }
    }
}
