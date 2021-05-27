package com.app.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import coil.network.HttpException
import com.app.core.common.LoadStateAdapter
import com.app.core.common.PagingInitialState
import com.app.core.extensions.visibleOrGone
import com.app.core.ui.MoviePagingAdapter
import com.app.firstsubmission.R
import com.app.firstsubmission.di.SearchFeatureDependencies
import com.app.search.databinding.FragmentSearchBinding
import com.app.search.di.DaggerSearchComponent
import com.app.search.viewmodel.ViewModelFactory
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okio.IOException
import reactivecircus.flowbinding.appcompat.queryTextChanges
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    private val movieAdapter = MoviePagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDaggerComponent()
        setupViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()

        observeMovies()
        observeMoviesState()

        doSearchMovie()
    }

    private fun clearViewReference() {
        binding.rvSearch.adapter = null
    }

    private fun injectDaggerComponent() {
        // Since Hilt doesn't work with dynamic feature modules, use plain Dagger instead.
        // cf. https://developer.android.com/training/dependency-injection/hilt-multi-module#dfm
        context?.let {
            DaggerSearchComponent.factory().create(
                EntryPointAccessors.fromApplication(it, SearchFeatureDependencies::class.java)
            ).inject(this)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]
    }

    private fun doSearchMovie() {
        binding.searchView.queryTextChanges()
            .skipInitialValue()
            .onEach { query ->
                viewModel.searchMovie(query.toString())
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupToolbar() {
        binding.toolbarSearch.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        movieAdapter.onMovieClicked = { movie ->
            val direction = SearchFragmentDirections.actionSearchToDetailFragment(movie.id)
            findNavController().navigate(direction)
        }

        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter.withLoadStateFooter(
                footer = LoadStateAdapter { movieAdapter.retry() }
            )
        }
    }

    private fun observeMovies() {
        viewModel.searchResult.observe(viewLifecycleOwner, { movies ->
            lifecycleScope.launch {
                movieAdapter.submitData(movies)
            }
        })
    }

    private fun observeMoviesState() {
        movieAdapter.addLoadStateListener { loadState ->

            val isLoading = loadState.source.refresh is LoadState.Loading
                    && loadState.source.append is LoadState.Loading
            binding.rvSearch.visibleOrGone(!isLoading)

            val isFirstLoading = loadState.source.refresh is LoadState.Loading
            binding.progressBar.root.visibleOrGone(isFirstLoading)



            if (isFirstLoading) binding.emptyState.root.visibleOrGone(false)

            val isFirstLoadError = loadState.source.refresh is LoadState.Error
            if (isFirstLoadError) {
                val error = loadState.source.refresh as LoadState.Error
                when (error.error) {
                    is IOException -> shouldShowEmptyState(
                        true,
                        PagingInitialState.CONNECTION_ERROR
                    )
                    is HttpException -> shouldShowEmptyState(
                        true,
                        PagingInitialState.SERVER_ERROR
                    )
                    else -> shouldShowEmptyState(
                        true,
                        PagingInitialState.UNKNOWN_ERROR
                    )
                }
            } else {
                if (loadState.append.endOfPaginationReached && movieAdapter.itemCount < 1) {
                    shouldShowEmptyState(true, PagingInitialState.SEARCH_NOT_FOUND)
                } else {
                    shouldShowEmptyState(false)
                }
            }

        }
    }

    private fun setEmptyState(error: PagingInitialState) {
        var icon = 0
        var title = ""
        var desc = ""
        var shouldShowButton = false

        when (error) {
            PagingInitialState.CONNECTION_ERROR -> {
                icon = R.drawable.ic_empty_state_no_connection
                title = resources.getString(R.string.error_connection_title)
                desc = resources.getString(R.string.error_connection_desc)
                shouldShowButton = true
            }

            PagingInitialState.SERVER_ERROR -> {
                title = resources.getString(R.string.error_server_title)
                desc = resources.getString(R.string.error_server_desc)
                shouldShowButton = true
            }

            PagingInitialState.UNKNOWN_ERROR -> {
                title = resources.getString(R.string.error_unknown_title)
                desc = resources.getString(R.string.error_unknown_desc)
                shouldShowButton = true
            }

            PagingInitialState.SEARCH_NOT_FOUND -> {
                icon = R.drawable.ic_empty_state_not_found
                title = resources.getString(R.string.error_empty_result_title)
                desc = resources.getString(R.string.error_empty_result_desc)
                shouldShowButton = false
            }

            PagingInitialState.NO_ERROR -> {
            }
        }

        val shouldShowIcon = icon != 0
        binding.emptyState.imgEmptyState.visibleOrGone(shouldShowIcon)
        binding.emptyState.imgEmptyState.setImageResource(icon)

        binding.emptyState.tvEmptyStateTitle.text = title
        binding.emptyState.tvEmptyStateDesc.text = desc

        binding.emptyState.btnEmptyState.visibleOrGone(shouldShowButton)
        binding.emptyState.btnEmptyState.setOnClickListener {
            movieAdapter.refresh()
        }
    }

    private fun shouldShowEmptyState(
        show: Boolean,
        pagingInitialState: PagingInitialState = PagingInitialState.NO_ERROR
    ) = if (show) {
        setEmptyState(pagingInitialState)
        binding.emptyState.root.visibleOrGone(true)
        binding.rvSearch.visibleOrGone(false)
    } else {
        binding.emptyState.root.visibleOrGone(false)
        binding.rvSearch.visibleOrGone(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearViewReference()
        _binding = null
    }

}