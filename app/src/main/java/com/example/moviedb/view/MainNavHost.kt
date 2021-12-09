package com.example.moviedb.view

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.moviedb.R
import com.example.moviedb.databinding.MainNavHostBinding
import com.example.moviedb.util.editable
import com.example.moviedb.viewmodel.TMDBViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNavHost : AppCompatActivity() {

    private val binding by lazy { MainNavHostBinding.inflate(layoutInflater) }

    private val tmdbViewModel by viewModels<TMDBViewModel>()

    private val navHostFragment by lazy {

        supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment

    }

    private lateinit var appBarConfig: AppBarConfiguration

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.BLACK
        window.statusBarColor = Color.BLACK
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        initListeners()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        this.menu = menu

        menuInflater.inflate(when(

            applicationContext.resources.configuration.uiMode
                .and(Configuration.UI_MODE_NIGHT_MASK)

        ) {

            Configuration.UI_MODE_NIGHT_YES -> {R.menu.toolbar_night}
            Configuration.UI_MODE_NIGHT_NO -> {R.menu.toolbar_day}
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {R.menu.toolbar_day}
            else -> {R.menu.toolbar_day}

        }, menu)

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {

            R.id.menu_action_search -> {

                onClickSearch()
                true

            }

            R.id.menu_action_clear_search -> {

                onClickSearchClear()
                true

            }

            else -> super.onOptionsItemSelected(item)

        }

    }

    override fun onSupportNavigateUp() = with(navHostFragment.navController) {

        navigateUp(appBarConfig) || super.onSupportNavigateUp()

    }

    private fun initViews() {

        setSupportActionBar(binding.toolbar)

        lifecycleScope.launchWhenCreated {

            navHostFragment.navController.apply {

                graph = navInflater.inflate(R.navigation.main_nav_graph)

                appBarConfig = AppBarConfiguration(setOf(

                    R.id.home_fragment

                ))

                setupActionBarWithNavController(this, appBarConfig)

            }

        }

    }

    private fun initListeners() = with(binding) {

        mainContent.etSearch.setOnEditorActionListener { v, _, _ ->

            hideSoftKeyboard(mainContent.etSearch)
            tmdbViewModel.searchMovies(v.text.toString(), 1)
            binding.mainContent.tilSearch.visibility = View.GONE

            true

        }

    }

    private fun onClickSearch() {

        val tilSearch = binding.mainContent.tilSearch

        if (tilSearch.visibility == View.VISIBLE) {

            tilSearch.visibility = View.GONE
            hideSoftKeyboard(binding.mainContent.etSearch)

        } else {

            tilSearch.visibility = View.VISIBLE
            tilSearch.requestFocus()
            showSoftKeyboard(binding.mainContent.etSearch)

        }

    }

    private fun onClickSearchClear() {

        val tilSearch = binding.mainContent.tilSearch

        if (tilSearch.visibility == View.VISIBLE) {

            tilSearch.visibility = View.GONE
            hideSoftKeyboard(binding.mainContent.etSearch)

        }

        tilSearch.editText?.text = "".editable

        tmdbViewModel.getPopularMovies(1)

    }

    private fun hideSoftKeyboard(et: TextInputEditText) =
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(et.windowToken, 0)

    private fun showSoftKeyboard(et: TextInputEditText) =
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(et, 0)

}