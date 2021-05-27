package com.app.firstsubmission.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.core.common.LoadStateAdapter
import com.app.core.common.PagingInitialState
import com.app.core.extensions.showToast
import com.app.core.extensions.throttleFirst
import com.app.core.extensions.visibleOrGone
import com.app.core.ui.MoviePagingAdapter
import com.app.core.utils.Constants.FAVORITE_MODULE
import com.app.core.utils.Constants.SEARCH_MODULE
import com.app.firstsubmission.R
import com.app.firstsubmission.databinding.FragmentHomeBinding
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okio.IOException
import reactivecircus.flowbinding.android.view.clicks
import retrofit2.HttpException

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val popularMovieAdapter = MoviePagingAdapter()
    private lateinit var splitInstallManager: SplitInstallManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        splitInstallManager = SplitInstallManagerFactory.create(context)

        setupActionBar()
        setupAction()
        setupRecyclerView()

        observePopularMovies()
        observePopularMoviesState()

    }

    private fun setupRecyclerView() {
        popularMovieAdapter.onMovieClicked = { movie ->
            val direction = HomeFragmentDirections.actionHomeToDetailFragment(movie.id)
            findNavController().navigate(direction)
        }
        binding.rvPopularMovies.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = popularMovieAdapter.withLoadStateFooter(
                footer = LoadStateAdapter { popularMovieAdapter.retry() }
            )
        }
    }

    private fun setupActionBar() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarHome)
    }

    private fun clearViewReference() {
        binding.rvPopularMovies.adapter = null
        (activity as AppCompatActivity).setSupportActionBar(null)
    }

    private fun setupAction() {
        binding.edtSearch.clicks()
            .throttleFirst(3000)
            .onEach {
                installOrLaunchSearchFeature()
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun installOrLaunchSearchFeature() {
        if (splitInstallManager.installedModules.contains(SEARCH_MODULE)) {
            findNavController().navigate(R.id.actionHomeToSearch)
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(SEARCH_MODULE)
                .build()

            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    findNavController().navigate(R.id.actionHomeToSearch)
                }
                .addOnFailureListener {
                    showToast(
                        resources.getString(
                            R.string.error_installing_module,
                            SEARCH_MODULE
                        )
                    )
                }
        }
    }

    private fun installOrLaunchFavoriteFeature() {
        if (splitInstallManager.installedModules.contains(FAVORITE_MODULE)) {
            findNavController().navigate(R.id.actionHomeToFavorite)
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(FAVORITE_MODULE)
                .build()

            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    findNavController().navigate(R.id.actionHomeToFavorite)
                }
                .addOnFailureListener {
                    showToast(
                        resources.getString(
                            R.string.error_installing_module,
                            FAVORITE_MODULE
                        )
                    )
                }
        }
    }

    private fun observePopularMoviesState() {
        popularMovieAdapter.addLoadStateListener { loadState ->

            val isLoading = loadState.source.refresh is LoadState.Loading
                    && loadState.source.append is LoadState.Loading
            binding.rvPopularMovies.visibleOrGone(!isLoading)

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
                shouldShowEmptyState(false)
            }

        }
    }

    private fun observePopularMovies() {
        viewModel.popularMovies.observe(viewLifecycleOwner, { movies ->
            lifecycleScope.launch {
                popularMovieAdapter.submitData(movies)
            }
        })
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

            PagingInitialState.NO_ERROR -> { }
        }

        val shouldShowIcon = icon != 0
        binding.emptyState.imgEmptyState.visibleOrGone(shouldShowIcon)
        binding.emptyState.imgEmptyState.setImageResource(icon)

        binding.emptyState.tvEmptyStateTitle.text = title
        binding.emptyState.tvEmptyStateDesc.text = desc

        binding.emptyState.btnEmptyState.visibleOrGone(shouldShowButton)
        binding.emptyState.btnEmptyState.setOnClickListener {
            popularMovieAdapter.refresh()
        }
    }

    private fun shouldShowEmptyState(
        show: Boolean,
        pagingInitialState: PagingInitialState = PagingInitialState.NO_ERROR
    ) = if (show) {
        setEmptyState(pagingInitialState)
        binding.emptyState.root.visibleOrGone(true)
        binding.rvPopularMovies.visibleOrGone(false)
    } else {
        binding.emptyState.root.visibleOrGone(false)
        binding.rvPopularMovies.visibleOrGone(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearViewReference()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                installOrLaunchFavoriteFeature()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}