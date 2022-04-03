package com.taufik.gitser.ui.activity.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.gitser.R
import com.taufik.gitser.adapter.search.SearchAdapter
import com.taufik.gitser.data.viewmodel.search.SearchViewModel
import com.taufik.gitser.databinding.ActivitySearchBinding
import com.taufik.gitser.utils.Utils.isNetworkEnabled
import es.dmoral.toasty.Toasty

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter
    private var textQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        checkConnectionEnabled()
        showNoSearchUser(true)
    }

    private fun initActionBar() {
        supportActionBar?.apply {
            title = "Cari"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun checkConnectionEnabled() {
        if (isNetworkEnabled(this)) {
            showNoNetworkConnection(false)
            setAdapter()
        } else {
            showNoNetworkConnection(true)
        }
    }

    private fun showNoNetworkConnection(isShow: Boolean) {
        binding.apply {
            if (isShow) {
                shimmerLoadingSearch.visibility = View.GONE
                rvSearchUsers.visibility = View.GONE
                layoutNoConnection.visibility = View.VISIBLE
            } else {
                shimmerLoadingSearch.visibility = View.VISIBLE
                rvSearchUsers.visibility = View.VISIBLE
                layoutNoConnection.visibility = View.GONE
            }
        }
    }

    private fun setAdapter() {
        searchAdapter = SearchAdapter()
        showLoading(false)
        binding.apply {
            with(rvSearchUsers) {
                layoutManager = LinearLayoutManager(this@SearchActivity)
                setHasFixedSize(true)
                adapter = searchAdapter
            }
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding.apply {
            if (isShow) {
                shimmerLoadingSearch.visibility = View.VISIBLE
                rvSearchUsers.visibility = View.GONE
                layoutNoConnection.visibility = View.GONE
                viewNoDataVisibility.visibility = View.GONE
            } else {
                shimmerLoadingSearch.visibility = View.GONE
                rvSearchUsers.visibility = View.VISIBLE
                layoutNoConnection.visibility = View.GONE
                viewNoDataVisibility.visibility = View.GONE
            }
        }
    }

    private fun showEmptyResult(isShow: Boolean) {
        binding.apply {
            if (isShow) {
                viewNoDataVisibility.visibility = View.VISIBLE
                viewResultsVisibility.visibility = View.GONE
            } else {
                viewNoDataVisibility.visibility = View.GONE
                viewResultsVisibility.visibility = View.VISIBLE
            }
        }
    }

    private fun showNoSearchUser(isShow: Boolean) {
        binding.apply {
            if (isShow) {
                viewNoSearchUser.visibility = View.VISIBLE
            } else {
                viewNoSearchUser.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.tvSearchUser)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    when {
                        query.isNotEmpty() -> {
                            textQuery = query
                            checkConnectionEnabled()
                            showSearchData(query)
                            clearFocus()
                        }

                        query.isEmpty() -> {
                            Toasty.normal(
                                this@SearchActivity,
                                "Silakan mengisi pencarian",
                                Toast.LENGTH_SHORT
                            ).show()
                            clearFocus()
                        }
                    }

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })

            return true
        }
    }

    private fun showSearchData(query: String) {
        binding.apply {

            showLoading(true)
            showNoSearchUser(false)
            showEmptyResult(false)

            viewModel = ViewModelProvider(this@SearchActivity, ViewModelProvider.NewInstanceFactory())[SearchViewModel::class.java]
            viewModel.apply {
                setSearchUsers(query)
                getSearchUsers().observe(this@SearchActivity) {
                    if (it != null) {
                        if (it.size != 0) {
                            viewResultsVisibility.visibility = View.VISIBLE
                            tvResultsCount.text = String.format(
                                getString(R.string.tvShowing),
                                it.size.toString()
                            )
                            searchAdapter.setSearchUserList(it)
                            showLoading(false)
                            showEmptyResult(false)
                            showNoSearchUser(false)
                        } else {
                            showLoading(false)
                            showEmptyResult(true)
                            showNoSearchUser(false)
                        }
                    } else {
                        showLoading(false)
                        showEmptyResult(true)
                        showNoSearchUser(false)
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}