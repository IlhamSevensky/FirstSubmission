package com.app.firstsubmission.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.core.domain.model.Movie
import com.app.core.extensions.*
import com.app.core.vo.Resource
import com.app.firstsubmission.R
import com.app.firstsubmission.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    private val movieId: Int by lazy {
        DetailFragmentArgs.fromBundle(arguments as Bundle).movieId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setMovie(movieId)
        observeMovie()

    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.detailToolbar)
        binding.collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
        binding.detailToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun clearViewReference(){
        (activity as AppCompatActivity).setSupportActionBar(null)
    }

    private fun setMovie(movieId: Int) = viewModel.getDetailMovie(movieId)

    private fun observeMovie() {
        viewModel.movie.observe(viewLifecycleOwner, { resource ->
            resource?.let { state ->
                when (state) {
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        displayData(state.data)
                    }

                    is Resource.Error -> {
                        state.exception.message?.let { msg ->
                            this.showToast(msg)
                        }
                    }

                    is Resource.ConnectionError -> {
                        state.exception.message?.let { msg ->
                            this.showToast(msg)
                        }
                    }

                    is Resource.HttpError -> {
                        state.exception.message?.let { msg ->
                            this.showToast(msg)
                        }
                    }
                }
            }
        })
    }

    private fun displayData(movie: Movie) {
        with(binding) {

            movie.backdrop_path?.let { path ->
                imgDetailHighlight.loadUrl(path)
            }

            movie.poster_path?.let { path ->
                contentDetail.imgMoviePoster.loadUrlRounded(path, 16F)
            }

            setFavoriteMovieIcon(movie.isFavorite)

            contentDetail.tvTitle.text = movie.title

            contentDetail.tvReleaseDate.text = movie.release_date.formatDatePattern()

            contentDetail.tvDescription.text = movie.overview

            contentDetail.ratingBarMovie.rating = movie.vote_average.calculateWithMaxRating(10)

            fabFavorite.setOnClickListener {
                if (movie.isFavorite) {
                    viewModel.removeFavoriteMovie(movie.id)
                    movie.isFavorite = false
                    setFavoriteMovieIcon(false)
                } else {
                    viewModel.insertFavoriteMovie(movie)
                    movie.isFavorite = true
                    setFavoriteMovieIcon(true)
                }
            }

        }
    }

    private fun setFavoriteMovieIcon(isFavorite: Boolean) = binding.fabFavorite.setImageResource(
        if (isFavorite) R.drawable.ic_favorite_active else R.drawable.ic_favorite_inactive
    )

    override fun onDestroyView() {
        super.onDestroyView()
        clearViewReference()
        _binding = null
    }

}