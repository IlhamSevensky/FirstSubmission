package com.app.firstsubmission.ui.detail

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.core.domain.model.Movie
import com.app.core.extensions.*
import com.app.core.utils.Constants.MIMES_IMAGE
import com.app.core.vo.Resource
import com.app.firstsubmission.R
import com.app.firstsubmission.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream


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
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.detailToolbar)
        binding.collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
        binding.detailToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                processShareMovie()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun processShareMovie() {
        val bitmap = binding.contentDetail.imgMoviePoster.drawable.toBitmap()
        val uri = bitmap.saveToInternalStorage(requireContext())
        val content = binding.contentDetail.tvTitle.text.toString()
        if (content.isEmpty()) return
        shareMovie(requireContext(), uri, content)
    }

    private fun clearViewReference() {
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

    private fun shareMovie(context: Context, uri: Uri, content: String) {
        val fileInputStream = FileInputStream(uri.path)
        val bitmap = fileInputStream.use { inputStream ->
            return@use BitmapFactory.decodeStream(inputStream)
        }

        try {
            val file = File("${context.cacheDir}/movie.png")
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, FileOutputStream(file))
            val contentUri =
                FileProvider.getUriForFile(context, context.packageName + ".provider", file)

            doShareIntent(contentUri, content)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun doShareIntent(uri: Uri?, content: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, content)
            putExtra(Intent.EXTRA_STREAM, uri)
            type = MIMES_IMAGE
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        this.startActivity(shareIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearViewReference()
        _binding = null
    }

}