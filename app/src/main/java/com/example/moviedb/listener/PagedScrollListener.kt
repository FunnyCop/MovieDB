package com.example.moviedb.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PagedScrollListener(

    private val layoutManager: LinearLayoutManager

) : RecyclerView.OnScrollListener() {

    abstract fun loadMoreItems()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (visibleItemCount + firstItemPosition >= totalItemCount && firstItemPosition >= 0)
            loadMoreItems()

        super.onScrolled(recyclerView, dx, dy)

    }

}