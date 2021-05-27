package com.app.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM favorite_movie")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movies: List<MovieEntity>)

    @Query("DELETE FROM favorite_movie WHERE id = :movieId")
    suspend fun removeFavoriteMovie(movieId: Int)

    @Query("SELECT * FROM favorite_movie WHERE id = :movieId")
    fun isFavoriteMovie(movieId: Int) : MovieEntity?

}