package com.app.core.data.source.local.entity

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie")
data class MovieEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @Nullable
    @ColumnInfo(name = "release_date")
    val release_date: String? = "-",

    @Nullable
    @ColumnInfo(name = "poster_path")
    val poster_path: String?,

    @Nullable
    @ColumnInfo(name = "backdrop_path")
    val backdrop_path: String?,

    @Nullable
    @ColumnInfo(name = "vote_average")
    val vote_average: Float? = 0F,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean
)