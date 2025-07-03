package com.taufik.consumerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.gitser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) { ActivityMainBinding.inflate(layoutInflater) }
    private var viewModel: FavoriteViewModel? = null
    private val searchAdapter by lazy { SearchAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setAdapter()
        setData()
    }

    private fun setAdapter() {
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
        viewModel?.apply {
            setFavoriteUser(this@MainActivity)
            getFavoriteUser().observe(this@MainActivity) {
                if (it != null) {
                    searchAdapter.setSearchUserList(it)
                }
            }
        }
    }
}