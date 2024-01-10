package com.alexmercerind.starwars.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexmercerind.starwars.model.Character

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(values: List<Character>)

    @Query("SELECT * FROM Character WHERE page = :page")
    suspend fun getCharacterPage(page: Int): List<Character>
}
