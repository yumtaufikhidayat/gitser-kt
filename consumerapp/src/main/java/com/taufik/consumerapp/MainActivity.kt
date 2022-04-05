package com.taufik.consumerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        setData()
    }

    private fun setAdapter() {
        searchAdapter = SearchAdapter()
        binding.apply {
            with(rvFavorite) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = searchAdapter
            }
        }
    }

    private fun setData() {
        viewModel = ViewModelProvider(this)[(FavoriteViewModel::class.java)]
        viewModel.apply {
            setFavoriteUser(this@MainActivity)
            getFavoriteUser().observe(this@MainActivity) {
                if (it != null) {
                    searchAdapter.setSearchUserList(it)
                }
            }
        }
    }
}