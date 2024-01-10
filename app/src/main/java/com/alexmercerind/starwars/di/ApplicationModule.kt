package com.alexmercerind.starwars.di

import android.content.Context
import androidx.room.Room
import com.alexmercerind.starwars.api.StarWarsAPI
import com.alexmercerind.starwars.db.StarWarsDatabase
import com.alexmercerind.starwars.repository.StarWarsRepository
import com.alexmercerind.starwars.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideStarWarsAPI(): StarWarsAPI {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StarWarsAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideStarWarsDatabase(@ApplicationContext applicationContext: Context): StarWarsDatabase {
        return Room.databaseBuilder(
            applicationContext,
            StarWarsDatabase::class.java,
            "star-wars-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideStarWarsRepository(api: StarWarsAPI, database: StarWarsDatabase): StarWarsRepository {
        return StarWarsRepository(api, database)
    }
}