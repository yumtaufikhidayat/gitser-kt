package com.taufik.gitser.ui.activity.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.gitser.adapter.SearchAdapter
import com.taufik.gitser.data.db.Favorite
import com.taufik.gitser.data.model.search.Search
import com.taufik.gitser.data.viewmodel.favorite.FavoriteViewModel
import com.taufik.gitser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: SearchAdapter

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

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.title = "Favorit"
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setAdapter() {

        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()
    }

    private fun setViewModel() {

        viewModel = ViewModelProvider(this)
                .get(FavoriteViewModel::class.java)
    }

    private fun setData() {

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this, {
            if (it != null) {
                val list = mapList(it)
                adapter.setSearchUserList(list)
            }
        })
    }

    private fun mapList(users: List<Favorite>): ArrayList<Search> {

        val listOfUsers = ArrayList<Search>()

        for (user in users) {

            val userMapped = Search (
                user.id,
                user.login,
                user.avatarUrl
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