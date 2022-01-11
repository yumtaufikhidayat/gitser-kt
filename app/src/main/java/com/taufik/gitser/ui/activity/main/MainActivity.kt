package com.taufik.gitser.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.gitser.R
import com.taufik.gitser.adapter.search.SearchAdapter
import com.taufik.gitser.data.viewmodel.main.MainViewModel
import com.taufik.gitser.databinding.ActivityMainBinding
import com.taufik.gitser.ui.activity.settings.SettingsActivity
import com.taufik.gitser.ui.activity.favorite.FavoriteActivity
import com.taufik.gitser.ui.activity.profile.ProfileActivity
import com.taufik.gitser.ui.activity.search.SearchActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var searchAdapter: SearchAdapter
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()

        setViewModel()

        setData()
    }

    private fun setAdapter() {
        searchAdapter = SearchAdapter()
    }

    private fun setViewModel() {

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)
    }

    private fun setData() {

        showLoading(true)

        binding.apply {
            with(rvMain) {
                layoutManager = LinearLayoutManager(this@MainActivity)
                setHasFixedSize(true)
                adapter = searchAdapter
            }

            viewModel.setAllUsers()
            viewModel.getAllUsers().observe(this@MainActivity, {
                if (it != null) {
                    searchAdapter.setSearchUserList(it)
                    showLoading(false)
                }
            })
        }
    }

    private fun showLoading(state: Boolean) {

        binding.apply {
            if (state) {
                progressBarMain.visibility = View.VISIBLE
            } else {
                progressBarMain.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

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
            }, 2000)
    }
}