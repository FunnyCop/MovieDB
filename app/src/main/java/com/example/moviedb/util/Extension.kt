package com.example.moviedb.util

import android.text.Editable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {

    observe(lifecycleOwner, object : Observer<T> { override fun onChanged(t: T) {

        observer.onChanged(t)
        removeObserver(this)

    } })

}

val String.editable: Editable get() = Editable.Factory.getInstance().newEditable(this)