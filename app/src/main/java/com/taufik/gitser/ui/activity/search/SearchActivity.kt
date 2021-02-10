package com.taufik.gitser.ui.activity.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.gitser.adapter.SearchAdapter
import com.taufik.gitser.data.viewmodel.search.SearchViewModel
import com.taufik.gitser.databinding.ActivitySearchBinding
import es.dmoral.toasty.Toasty

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initActionBar()

        setAdapter()

        setViewModel()

        setData()

        setOnClickAction()
    }

    private fun initActionBar() {

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.title = "Cari"
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setAdapter() {

        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()
    }

    private fun setViewModel() {

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
                .get(SearchViewModel::class.java)
    }

    private fun setData() {

        binding.apply {
            rvSearchUsers.layoutManager = LinearLayoutManager(this@SearchActivity)
            rvSearchUsers.setHasFixedSize(true)
            rvSearchUsers.adapter = adapter
        }
    }

    private fun setOnClickAction() {

        binding.apply {
            imgSearch.setOnClickListener{
                searchUser()
            }

            etSearchQuery.setOnKeyListener { v, keyCode, event ->

                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }

                return@setOnKeyListener false
            }

            viewModel.getSearchUsers().observe(this@SearchActivity, {
                if (it != null) {
                    adapter.setSearchUserList(it)
                    showLoading(false)
                }
            })
        }
    }

    private fun searchUser() {

        binding.apply {

            val query = etSearchQuery.text.toString()

            if (query.isEmpty()) {
                Toasty.info(this@SearchActivity, "Silakan mengisi kolom pencarian", Toast.LENGTH_SHORT, true).show()
            }

            showLoading(true)

            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state: Boolean) {

        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}