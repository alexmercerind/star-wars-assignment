package com.alexmercerind.starwars.repository

import com.alexmercerind.starwars.api.StarWarsService
import com.alexmercerind.starwars.mappers.toCharacter
import com.alexmercerind.starwars.model.Character
import com.alexmercerind.starwars.paging.CharactersPageSource
import com.alexmercerind.starwars.utils.Result
import retrofit2.HttpException
import java.io.IOError

class StarWarsRepository() {
    suspend fun getCharacters(page: Int): Result<List<Character>> {
        return try {
            val response = StarWarsService.api.getCharacterPage(page)
            return Result.Success(response.results.map { it.toCharacter(page) })
        } catch (e: HttpException) {
            // NOTE:
            // Special case where status code is 404 if page limit is reached.
            // Thus, return empty List<Character>.
            if (e.code() == 404) {
                return Result.Success(listOf())
            }
            return Result.Error(e)
        } catch (e: IOError) {
            return Result.Error(e)
        }
    }
}
