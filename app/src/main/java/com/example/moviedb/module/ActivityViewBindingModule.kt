package com.example.moviedb.module

import android.content.Context
import android.view.LayoutInflater
import com.example.moviedb.databinding.MainNavHostBinding
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class ActivityViewBindingModule {

    @Provides
    fun provideMainNavHostBinding(@ActivityContext context: Context) =
        MainNavHostBinding.inflate(LayoutInflater.from(context))

}