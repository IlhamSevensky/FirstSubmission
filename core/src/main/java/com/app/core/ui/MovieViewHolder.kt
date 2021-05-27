package com.app.core.ui

import androidx.recyclerview.widget.RecyclerView
import com.app.core.databinding.ItemMovieBinding
import com.app.core.domain.model.Movie
import com.app.core.extensions.loadUrlRounded

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val callback: ((Movie) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        with(binding) {

            movie.poster_path?.let { path ->
                imgMoviePoster.loadUrlRounded(path, 16F)
            }

            tvMovieTitle.text = movie.title
            tvMovieDesc.text = movie.overview

            root.setOnClickListener {
                callback?.invoke(movie)
            }
        }
    }

}