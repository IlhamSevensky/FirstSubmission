package com.app.favorite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.core.domain.model.Movie
import com.app.core.domain.usecase.favorite_movie.GetFavoriteMoviesUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase
) : ViewModel() {

    init {
        getFavoriteMovies()
    }

    private val _favoriteMovies = MutableLiveData<List<Movie>>()

    val favoriteMovies: LiveData<List<Movie>>
        get() = _favoriteMovies

    private fun getFavoriteMovies() {
        viewModelScope.launch {
            getFavoriteMoviesUseCase.getFavoriteMovies().collect { movies ->
                _favoriteMovies.postValue(movies)
            }
        }
    }

}