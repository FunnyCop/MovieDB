package com.example.moviedb.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class MovieListLayoutManager(context: Context) : LinearLayoutManager(context) {

    var allowScroll = true

    override fun canScrollVertically() = if (allowScroll) super.canScrollVertically() else false

}