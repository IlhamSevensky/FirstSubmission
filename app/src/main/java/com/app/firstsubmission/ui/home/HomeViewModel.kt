package com.app.firstsubmission.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.core.domain.model.Movie
import com.app.core.domain.usecase.popular_movie.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    init {
        getPopularMovies()
    }

    private val _popularMovies = MutableLiveData<PagingData<Movie>>()
    val popularMovies: LiveData<PagingData<Movie>>
        get() = _popularMovies

    private fun getPopularMovies() = viewModelScope.launch {
        getPopularMoviesUseCase.getPopularMovies()
            .cachedIn(viewModelScope)
            .collectLatest { movies ->
                _popularMovies.postValue(movies)
            }
    }

}