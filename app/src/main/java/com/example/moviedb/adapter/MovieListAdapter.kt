package com.example.moviedb.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.databinding.ListItemBinding
import com.example.moviedb.model.Genre
import com.example.moviedb.model.Movie
import com.example.moviedb.util.TMDB_IMAGE_BASE_URL

class MovieListAdapter(

    private var movies: List<Movie>,
    private val genres: List<Genre>?,
    private val detailClick: (movie: Movie, genres: List<String>) -> Unit

) : RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieListViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {

        val movie = movies[position]
        val genreList = mutableListOf<String>()

        movie.genreIds.forEach { genreId ->

            genres?.firstOrNull { genre -> genre.id == genreId }
                ?.let { genreList.add(it.name) }

        }

        holder.loadItems(movies[position], genreList, detailClick)

    }

    override fun getItemCount() = movies.size

    fun setData(newMovies: List<Movie>) {

        val oldCount = movies.size

        if (newMovies.size > oldCount) {

            movies = newMovies
            notifyItemRangeInserted(oldCount, movies.size)

        } else if (newMovies.size < oldCount) {

            notifyItemRangeRemoved(0, oldCount)
            movies = newMovies
            notifyItemRangeInserted(0, newMovies.size)

        }

    }

    class MovieListViewHolder(

        private val binding: ListItemBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {

            fun getInstance(parent: ViewGroup) = ListItemBinding.inflate(

                LayoutInflater.from(parent.context),
                parent,
                false

            ).run { MovieListViewHolder(this) }

        }

        fun loadItems(

            movie: Movie,
            genres: List<String>,
            detailClick: (movie: Movie, genres: List<String>) -> Unit

        ) = with(binding) {

            tvTitle.text = movie.title
            tvGenre.text = if (genres.isNotEmpty()) genres[0] else ""
            tvPopularity.text = movie.popularity.toString()
            tvRelease.text = movie.releaseDate
            btnDetail.setOnClickListener { detailClick(movie, genres) }

            if (movie.posterPath != null)
                Glide.with(root)
                    .load("$TMDB_IMAGE_BASE_URL${movie.posterPath}")
                    .into(ivMain)

        }

    }

}