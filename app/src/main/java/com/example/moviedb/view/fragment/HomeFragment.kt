package com.example.moviedb.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviedb.adapter.MovieListAdapter
import com.example.moviedb.databinding.HomeFragmentBinding
import com.example.moviedb.layoutmanager.MovieListLayoutManager
import com.example.moviedb.listener.PagedScrollListener
import com.example.moviedb.model.Genre
import com.example.moviedb.model.Movie
import com.example.moviedb.util.TMDB_IMAGE_BASE_URL
import com.example.moviedb.util.convertToModelList
import com.example.moviedb.util.observeUntilNotEmpty
import com.example.moviedb.viewmodel.LocalGenreViewModel
import com.example.moviedb.viewmodel.TMDBViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var binding: HomeFragmentBinding

    private val tmdbViewModel by activityViewModels<TMDBViewModel>()
    private val localGenreViewModel by activityViewModels<LocalGenreViewModel>()

    private var page = 1

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()

    }

    private fun initObservers() {

        localGenreViewModel.genres.observeUntilNotEmpty(viewLifecycleOwner) {

            if (it.isNotEmpty())
                initRvAdapter(it.convertToModelList())

        }

    }

    private fun initListeners() = with(binding) {

        detailOverlay.btnClose.setOnClickListener {

            detailOverlay.detailOverlay.visibility = View.GONE
            (rvMain.layoutManager as MovieListLayoutManager).allowScroll = true

        }

    }

    private fun initRvAdapter(genres: List<Genre>) = with(binding) {

        context?.let { rvMain.layoutManager = MovieListLayoutManager(it) }

        rvMain.adapter = MovieListAdapter(

            listOf(),
            genres,
            ::detailClick

        )

        rvMain.setHasFixedSize(false)

        rvMain.layoutManager?.let {

            rvMain.addOnScrollListener(

                object : PagedScrollListener(it as LinearLayoutManager) {

                    override fun loadMoreItems() {

                        val pages = tmdbViewModel.pages.value

                        page += 1

                        if (pages != null && pages >= page) {

                            val search = tmdbViewModel.search.value

                            if (search == null)
                                tmdbViewModel.getMorePopularMovies(page)

                            else tmdbViewModel.searchMoreMovies(search, page)

                        }

                    }

                }

            )

        }

        tmdbViewModel.movies.observe(viewLifecycleOwner) {

            if (it != null)
                (binding.rvMain.adapter as MovieListAdapter).setData(it)

            else {

                page = 1
                (binding.rvMain.adapter as MovieListAdapter).setData(listOf())

            }

        }

        tmdbViewModel.getPopularMovies(1)

    }

    @SuppressLint("SetTextI18n")
    private fun detailClick(movie: Movie, genres: List<String>) {

        (binding.rvMain.layoutManager as MovieListLayoutManager).allowScroll = false

        with(binding.detailOverlay) {

            detailOverlay.visibility = View.VISIBLE
            tvTitle.text = "Title: ${movie.title}"
            tvGenre.text = "Genre(s): ${genres.joinToString(", ")}"
            tvPopularity.text = "Popularity: ${movie.popularity}"
            tvRelease.text = "Release Date: ${movie.releaseDate}"
            tvOverview.text = "Overview: ${movie.overview}"

            if (movie.backdropPath != null)
                Glide.with(binding.root)
                    .load("$TMDB_IMAGE_BASE_URL${movie.backdropPath}")
                    .into(ivMain)

        }

    }

}