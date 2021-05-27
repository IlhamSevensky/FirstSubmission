package com.app.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.core.domain.model.Movie
import com.app.core.domain.usecase.search_movie.GetSearchMovieUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class SearchViewModel @Inject constructor(
    private val getSearchMovieUseCase: GetSearchMovieUseCase
) : ViewModel() {

    private var searchJob: Job? = null

    private val queryChannel = MutableSharedFlow<String>()

    val searchResult: LiveData<PagingData<Movie>> = queryChannel.asSharedFlow()
        .debounce(500)
        .distinctUntilChanged()
        .filter { query ->
            query.trim().isNotEmpty()
        }
        .flatMapLatest { query ->
            getSearchMovieUseCase.getSearchMovies(query)
        }
        .cachedIn(viewModelScope)
        .asLiveData()

    fun searchMovie(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            queryChannel.emit(query)
        }
    }

}