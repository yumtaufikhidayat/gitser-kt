package com.taufik.gitser.ui.activity.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.gitser.R
import com.taufik.gitser.adapter.search.SearchAdapter
import com.taufik.gitser.data.viewmodel.main.MainViewModel
import com.taufik.gitser.databinding.ActivityMainBinding
import com.taufik.gitser.ui.activity.favorite.FavoriteActivity
import com.taufik.gitser.ui.activity.profile.ProfileActivity
import com.taufik.gitser.ui.activity.search.SearchActivity
import com.taufik.gitser.ui.activity.settings.SettingsActivity
import com.taufik.gitser.utils.Utils.isNetworkEnabled
import com.taufik.gitser.utils.applyEdgeToEdgeInsets

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val searchAdapter by lazy {  SearchAdapter() }

    private val viewModel: MainViewModel by viewModels()
    private var doubleBackToExitPressedOnce = false
    private val delayTime = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        applyEdgeToEdgeInsets(window, binding.root)

        checkConnectionEnabled()
        setSwipeRefresh()
    }

    private fun checkConnectionEnabled() {
        if (isNetworkEnabled(this)) {
            initView()
            checkOrientation()
            initObserver()
        } else {
            showNoNetworkConnection(true)
        }
    }

    private fun setSwipeRefresh() {
        binding.apply {
            with(swipeRefreshMain) {
                setColorSchemeColors(getColor(R.color.purple_700))
                setOnRefreshListener { checkConnectionEnabled() }
            }
        }
    }

    private fun showNoNetworkConnection(isShow: Boolean) {
        binding.apply {
            if (isShow) {
                shimmerLoadingMain.visibility = View.GONE
                rvMain.visibility = View.GONE
                swipeRefreshMain.isRefreshing = false
                layoutNoConnectionVisibility.visibility = View.VISIBLE
                layoutNoConnection.btnRetry.setOnClickListener {
                    checkConnectionEnabled()
                }
            } else {
                layoutNoConnectionVisibility.visibility = View.GONE
            }
        }
    }

    private fun initView() {
        binding.apply {
            with(rvMain) {
                layoutManager = LinearLayoutManager(this@MainActivity)
                setHasFixedSize(true)
                adapter = searchAdapter
            }
        }
    }

    private fun initObserver() {
        viewModel.apply {
            isLoading.observe(this@MainActivity) {
                showLoading(it)
            }

            listAllUsers.observe(this@MainActivity) {
                if (it != null) {
                    showNoNetworkConnection(false)
                    searchAdapter.submitList(it)
                }
            }
        }
    }

    private fun checkOrientation() = with(binding) {
        rvMain.layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(this@MainActivity, 2)
        } else {
            LinearLayoutManager(this@MainActivity)
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding.apply {
            if (isShow) {
                shimmerLoadingMain.visibility = View.VISIBLE
                rvMain.visibility = View.GONE
                swipeRefreshMain.isRefreshing = true
            } else {
                shimmerLoadingMain.visibility = View.GONE
                rvMain.visibility = View.VISIBLE
                swipeRefreshMain.isRefreshing = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_search_main -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_profile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Klik sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper())
            .postDelayed({
                doubleBackToExitPressedOnce = false
            }, delayTime)
    }
}