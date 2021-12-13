package com.example.moviedb.util

import android.text.Editable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.moviedb.entity.GenreEntity
import com.example.moviedb.model.Genre
import com.example.moviedb.model.Movie

/** Observe [LiveData] once */
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {

    observe(lifecycleOwner, object : Observer<T> { override fun onChanged(t: T) {

        observer.onChanged(t)
        removeObserver(this)

    } })

}

/** Observe [LiveData] until it is not null */
fun <T> LiveData<T>.observeUntilNotNull(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {

    observe(lifecycleOwner, object : Observer<T> { override fun onChanged(t: T) {

        observer.onChanged(t)

        if (t != null)
            removeObserver(this)

    } })

}

/** Observe [LiveData] ([List]) until it is not empty. */
fun <T> LiveData<List<T>>.observeUntilNotEmpty(lifecycleOwner: LifecycleOwner, observer: Observer<List<T>>) {

    observe(lifecycleOwner, object : Observer<List<T>> { override fun onChanged(t: List<T>) {

        observer.onChanged(t)

        if (t.isNotEmpty())
            removeObserver(this)

    } })

}

/** Get [Editable] from [String]. */
val String.editable: Editable get() = Editable.Factory.getInstance().newEditable(this)

/** Convert [Genre] to [GenreEntity]. */
fun Genre.convertToEntity() = GenreEntity(id, name)

/** Convert [List] ([Genre]) to [List] ([GenreEntity]). */
fun List<Genre>.convertToEntityList(): List<GenreEntity> {

    val genreEntityList = mutableListOf<GenreEntity>()

    forEach { genreEntityList.add(it.convertToEntity()) }

    return genreEntityList

}

/** Convert [GenreEntity] to [Genre]. */
fun GenreEntity.convertToModel() = Genre(id, name)

/** Convert [List] ([GenreEntity]) to [List] ([Genre]). */
fun List<GenreEntity>.convertToModelList(): List<Genre> {

    val genreModelList = mutableListOf<Genre>()

    forEach { genreModelList.add(it.convertToModel()) }

    return genreModelList

}