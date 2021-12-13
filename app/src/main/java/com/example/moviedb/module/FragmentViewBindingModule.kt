package com.example.moviedb.module

import android.content.Context
import android.view.LayoutInflater
import com.example.moviedb.databinding.HomeFragmentBinding
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(FragmentComponent::class)
class FragmentViewBindingModule {

    @Provides
    fun provideHomeFragmentBinding(@ActivityContext context: Context) =
        HomeFragmentBinding.inflate(LayoutInflater.from(context))

}