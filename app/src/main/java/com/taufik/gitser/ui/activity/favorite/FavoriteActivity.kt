package com.taufik.gitser.ui.activity.favorite

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.gitser.R
import com.taufik.gitser.adapter.search.SearchAdapter
import com.taufik.gitser.data.local.FavoriteEntity
import com.taufik.gitser.data.response.search.Search
import com.taufik.gitser.data.viewmodel.favorite.FavoriteViewModel
import com.taufik.gitser.databinding.ActivityFavoriteBinding
import com.taufik.gitser.utils.applyEdgeToEdgeInsets

class FavoriteActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFavoriteBinding.inflate(layoutInflater) }
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var searchdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        applyEdgeToEdgeInsets(window, binding.root)

        initActionBar()
        setAdapter()
        checkOrientation()
        setData()
    }

    private fun initActionBar() {
        supportActionBar?.apply {
            title = getString(R.string.tvFavorite)
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

    private fun checkOrientation() = with(binding) {
        rvFavorite.layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(this@FavoriteActivity, 2)
        } else {
            LinearLayoutManager(this@FavoriteActivity)
        }
    }

    private fun showNoFavorite(isShow: Boolean) = with(binding) {
        if (isShow) {
            layoutNoFavoriteVisibility.visibility = View.VISIBLE
            layoutNoFavorite.apply {
                imgNoNetworkConnection.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@FavoriteActivity,
                        R.drawable.ic_no_repository
                    )
                )
                tvNoConnectionTitle.text = getString(R.string.tvNoFavorite)
                tvNoConnectionDesc.text = getString(R.string.tvLetsFavorite)
                btnRetry.visibility = View.GONE
            }
            rvFavorite.visibility = View.GONE
        } else {
            layoutNoFavoriteVisibility.visibility = View.GONE
        }
    }

    private fun setData() {
        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                if (it.isNotEmpty()) {
                    val list = mapList(it)
                    searchdapter.submitList(list)
                    showNoFavorite(false)
                } else {
                    showNoFavorite(true)
                }
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