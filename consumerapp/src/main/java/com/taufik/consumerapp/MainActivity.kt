package com.taufik.consumerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FavoriteViewModel
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

        viewModel = ViewModelProvider(this)
            .get(FavoriteViewModel::class.java)
    }

    private fun setData() {

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@MainActivity)
            rvFavorite.adapter = adapter
        }

        viewModel.setFavoriteUser(this)
        viewModel.getFavoriteUser().observe(this, {
            if (it != null) {
                adapter.setSearchUserList(it)
            }
        })
    }
}