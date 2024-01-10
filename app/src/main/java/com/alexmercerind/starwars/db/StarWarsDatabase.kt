package com.alexmercerind.starwars.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alexmercerind.starwars.db.converters.ListConverter
import com.alexmercerind.starwars.db.dao.CharacterDao
import com.alexmercerind.starwars.db.dao.FilmDao
import com.alexmercerind.starwars.model.Character
import com.alexmercerind.starwars.model.Film

@Database(entities = [Character::class, Film::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class StarWarsDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun filmDao(): FilmDao

    companion object {
        @Volatile
        private var instance: StarWarsDatabase? = null
        private val lock = Any()
        operator fun invoke(application: Application) = instance ?: synchronized(lock) {
            instance ?: createDatabase(application).also { instance = it }
        }

        private fun createDatabase(application: Application) =
            Room.databaseBuilder(
                application,
                StarWarsDatabase::class.java,
                "star-wars-database"
            ).build()
    }
}
