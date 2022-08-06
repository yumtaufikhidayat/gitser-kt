package com.taufik.gitser.ui.activity.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.gitser.R
import com.taufik.gitser.adapter.search.SearchAdapter
import com.taufik.gitser.data.viewmodel.search.SearchViewModel
import com.taufik.gitser.databinding.ActivitySearchBinding
import com.taufik.gitser.utils.Utils.isNetworkEnabled
import es.dmoral.toasty.Toasty

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchAdapter

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        checkConnectionEnabled()
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
            initObserver()
            setAdapter()
        } else {
            showNoNetworkConnection(true)
        }
    }

    private fun initObserver() {
        viewModel.apply {
            listUsers.observe(this@SearchActivity) {
                searchAdapter.submitList(it)
            }

            isLoading.observe(this@SearchActivity) {
                showLoading(it)
            }
        }
    }

    private fun showNoNetworkConnection(isShow: Boolean) {
        binding.apply {
            if (isShow) {
                shimmerLoadingSearch.visibility = View.GONE
                rvSearchUsers.visibility = View.GONE
                layoutNoConnectionVisibility.visibility = View.VISIBLE
                layoutNoConnection.btnRetry.setOnClickListener {
                    checkConnectionEnabled()
                }
            } else {
                layoutNoConnectionVisibility.visibility = View.GONE
            }
        }
    }

    private fun setAdapter() {
        searchAdapter = SearchAdapter()
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
                layoutNoConnectionVisibility.visibility = View.GONE
                layoutNoDataVisibility.visibility = View.GONE
            } else {
                shimmerLoadingSearch.visibility = View.GONE
                rvSearchUsers.visibility = View.VISIBLE
                layoutNoConnectionVisibility.visibility = View.GONE
                layoutNoDataVisibility.visibility = View.GONE
            }
        }
    }

    private fun showEmptyResult(isShow: Boolean) {
        binding.apply {
            if (isShow) {
                layoutNoDataVisibility.visibility = View.VISIBLE
                viewResultsVisibility.visibility = View.GONE
            } else {
                layoutNoDataVisibility.visibility = View.GONE
                viewResultsVisibility.visibility = View.VISIBLE
            }
        }
    }

    private fun showNoSearch(isShow: Boolean) = with(binding) {
        if (isShow) {
            shimmerLoadingSearch.visibility = View.GONE
            layoutNoSearchVisibility.visibility = View.VISIBLE
        } else {
            layoutNoSearchVisibility.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        checkConnectionEnabled()
        showNoSearch(true)
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
                            showNoSearch(false)
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
            showEmptyResult(false)
            viewModel.apply {
                setSearchUsers(query)
                listUsers.observe(this@SearchActivity) {
                    if (isNetworkEnabled(this@SearchActivity)) {
                        if (it != null) {
                            if (it.size != 0) {
                                viewResultsVisibility.visibility = View.VISIBLE
                                tvResultsCount.text = String.format(
                                    getString(R.string.tvShowing),
                                    it.size.toString()
                                )
                                searchAdapter.submitList(it)
                                showEmptyResult(false)
                            } else {
                                if (isNetworkEnabled(this@SearchActivity)) {
                                    showNoNetworkConnection(false)
                                    showEmptyResult(true)
                                } else {
                                    viewResultsVisibility.visibility = View.GONE
                                    showNoNetworkConnection(true)
                                }
                            }
                        } else {
                            if (isNetworkEnabled(this@SearchActivity)) {
                                showEmptyResult(true)
                                viewResultsVisibility.visibility = View.GONE
                                showNoNetworkConnection(false)
                            } else {
                                showEmptyResult(false)
                                viewResultsVisibility.visibility = View.GONE
                                showNoNetworkConnection(true)
                            }
                        }
                    } else {
                        viewResultsVisibility.visibility = View.GONE
                        showNoNetworkConnection(true)
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