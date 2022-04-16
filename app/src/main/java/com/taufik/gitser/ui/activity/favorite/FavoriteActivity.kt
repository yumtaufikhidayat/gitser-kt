package com.taufik.gitser.ui.activity.favorite

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.gitser.adapter.search.SearchAdapter
import com.taufik.gitser.data.local.FavoriteEntity
import com.taufik.gitser.data.response.search.Search
import com.taufik.gitser.data.viewmodel.favorite.FavoriteViewModel
import com.taufik.gitser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var searchdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        setAdapter()
        setViewModel()
        setData()
    }

    private fun initActionBar() {
        supportActionBar?.apply {
            title = "Favorit"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setAdapter() {
        searchdapter = SearchAdapter()
        binding.apply {
            with(rvFavorite) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@FavoriteActivity)
                adapter = searchdapter
            }
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
    }

    private fun setData() {
        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                searchdapter.setSearchUserList(list)
            }
        }
    }

    private fun mapList(users: List<FavoriteEntity>): ArrayList<Search> {
        val listOfUsers = ArrayList<Search>()
        for (user in users) {
            val userMapped = Search (
                user.id,
                user.login,
                user.avatarUrl,
                user.type
            )
            listOfUsers.add(userMapped)
        }

        return listOfUsers
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}