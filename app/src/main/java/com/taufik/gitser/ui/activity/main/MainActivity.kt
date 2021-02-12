package com.taufik.gitser.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.gitser.R
import com.taufik.gitser.adapter.SearchAdapter
import com.taufik.gitser.data.viewmodel.main.MainViewModel
import com.taufik.gitser.databinding.ActivityMainBinding
import com.taufik.gitser.ui.activity.favorite.FavoriteActivity
import com.taufik.gitser.ui.activity.profile.ProfileActivity
import com.taufik.gitser.ui.activity.search.SearchActivity
import es.dmoral.toasty.Toasty

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()

        setViewModel()

        setData()
    }

    private fun setAdapter() {

        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()
    }

    private fun setViewModel() {

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)
    }

    private fun setData() {

        showLoading(true)

        binding.apply {
            rvMain.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMain.setHasFixedSize(true)
            rvMain.adapter = adapter

            viewModel.setAllUsers()
            viewModel.getAllUsers().observe(this@MainActivity, {
                if (it != null) {
                    adapter.setSearchUserList(it)
                    showLoading(false)
                }
            })
        }
    }

    private fun showLoading(state: Boolean) {

        if (state) {
            binding.progressBarMain.visibility = View.VISIBLE
        } else {
            binding.progressBarMain.visibility = View.GONE
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

            R.id.nav_settings -> Toasty.normal(this, "Settings", R.drawable.ic_settings).show()

            R.id.nav_profile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}