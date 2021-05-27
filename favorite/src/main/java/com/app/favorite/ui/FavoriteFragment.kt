package com.app.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.core.extensions.visibleOrGone
import com.app.core.ui.MovieAdapter
import com.app.favorite.databinding.FragmentFavoriteBinding
import com.app.favorite.di.DaggerFavoriteComponent
import com.app.favorite.viewmodel.ViewModelFactory
import com.app.firstsubmission.di.FavoriteFeatureDependencies
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding get() = _binding!!

    private lateinit var viewModel: FavoriteViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    private val movieAdapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDaggerComponent()
        setupViewModel()
    }

    private fun injectDaggerComponent() {
        // Since Hilt doesn't work with dynamic feature modules, use plain Dagger instead.
        // cf. https://developer.android.com/training/dependency-injection/hilt-multi-module#dfm
        context?.let {
            DaggerFavoriteComponent.factory().create(
                EntryPointAccessors.fromApplication(it, FavoriteFeatureDependencies::class.java)
            ).inject(this)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()

        observeFavoriteMovies()

    }

    private fun setupToolbar() {
        binding.toolbarFavoriteMovies.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun clearViewReference(){
        binding.rvFavoriteMovies.adapter = null
    }

    private fun setupRecyclerView() {
        movieAdapter.onMovieClicked = { movie ->
            val direction = FavoriteFragmentDirections.actionFavoriteToDetailFragment(movie.id)
            findNavController().navigate(direction)
        }

        binding.rvFavoriteMovies.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }
    }

    private fun observeFavoriteMovies() {
        viewModel.favoriteMovies.observe(viewLifecycleOwner, { movies ->
            movies?.let {
                if (it.isEmpty()) {
                    shouldShowEmptyFavoriteState(true)
                } else {
                    shouldShowEmptyFavoriteState(false)
                    movieAdapter.submitList(it)
                }
            }
        })
    }

    private fun configureFavoriteEmptyState() {
        binding.emptyState.apply {

            imgEmptyState.setBackgroundResource(com.app.firstsubmission.R.drawable.ic_empty_state_favorite)
            tvEmptyStateTitle.text =
                resources.getString(com.app.firstsubmission.R.string.error_empty_favorite_title)
            tvEmptyStateDesc.text =
                resources.getString(com.app.firstsubmission.R.string.error_empty_favorite_desc)

            btnEmptyState.visibleOrGone(false)
        }
    }


    private fun shouldShowEmptyFavoriteState(show: Boolean) = if (show) {
        configureFavoriteEmptyState()
        binding.emptyState.root.visibleOrGone(true)
        binding.rvFavoriteMovies.visibleOrGone(false)
    } else {
        binding.emptyState.root.visibleOrGone(false)
        binding.rvFavoriteMovies.visibleOrGone(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearViewReference()
        _binding = null
    }


}