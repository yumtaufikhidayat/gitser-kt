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
import es.dmoral.toasty.Toasty

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()
        setAdapter()
        setViewModel()
        setData()
    }

    private fun initActionBar() {
        supportActionBar?.apply {
            title = "Cari"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setAdapter() {
        searchAdapter = SearchAdapter()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[SearchViewModel::class.java]
    }

    private fun setData() {
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
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
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
                            searchUser(query)
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

    private fun searchUser(query: String) {
        showLoading(true)
        viewModel.setSearchUsers(query)
        viewModel.getSearchUsers().observe(this@SearchActivity) {
                binding.apply {
                if (it != null) {
                    viewResultsVisibility.visibility = View.VISIBLE
                    tvResultsCount.text = String.format(
                        "%s %s %s",
                        getString(R.string.tvShowing),
                        it.size.toString(),
                        getString(R.string.tvResult)
                    )
                    searchAdapter.setSearchUserList(it)
                    showLoading(false)
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