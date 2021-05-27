package com.app.firstsubmission.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.core.domain.model.Movie
import com.app.core.domain.usecase.detail_movie.GetDetailMovieUseCase
import com.app.core.domain.usecase.favorite_movie.InsertFavoriteMovieUseCase
import com.app.core.domain.usecase.favorite_movie.RemoveFavoriteMovieUseCase
import com.app.core.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailMovieUseCase: GetDetailMovieUseCase,
    private val insertFavoriteMovieUseCase: InsertFavoriteMovieUseCase,
    private val removeFavoriteMovieUseCase: RemoveFavoriteMovieUseCase
) : ViewModel() {

    private val _movie = MutableLiveData<Resource<Movie>>()

    val movie: LiveData<Resource<Movie>>
        get() = _movie

    fun getDetailMovie(movieId: Int) {
        viewModelScope.launch {
            getDetailMovieUseCase.getDetailMovie(movieId).collect { data ->
                _movie.postValue(data)
            }
        }
    }

    fun insertFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            insertFavoriteMovieUseCase.insertFavoriteMovie(movie)
        }
    }

    fun removeFavoriteMovie(movieId: Int) {
        viewModelScope.launch {
            removeFavoriteMovieUseCase.removeFavoriteMovie(movieId)
        }
    }
}