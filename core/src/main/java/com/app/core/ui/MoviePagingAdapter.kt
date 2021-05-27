package com.app.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.app.core.databinding.ItemMovieBinding
import com.app.core.domain.model.Movie

class MoviePagingAdapter :
    PagingDataAdapter<Movie, MovieViewHolder>(DIFF_CALLBACK) {

    var onMovieClicked: ((Movie) -> Unit)? = null

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(movie) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onMovieClicked
    )

}