package com.taufik.gitser.ui.activity.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.taufik.gitser.R
import com.taufik.gitser.adapter.PagerAdapter
import com.taufik.gitser.data.viewmodel.detail.DetailSearchViewModel
import com.taufik.gitser.databinding.ActivityDetailSearchBinding

class DetailSearchActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "com.taufik.gitser.ui.activity.detail.EXTRA_USERNAME"
    }

    private lateinit var binding: ActivityDetailSearchBinding
    private lateinit var viewModel: DetailSearchViewModel
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewModel()

        setViewPager()
    }

    private fun setViewModel() {

        val username = intent.getStringExtra(EXTRA_USERNAME)

        bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory())
            .get(DetailSearchViewModel::class.java)

        if (username != null) {
            viewModel.setDetailSearch(username)
            viewModel.getDetailSearch().observe(this, {
                if (it != null) {
                    binding.apply {

                        Glide.with(this@DetailSearchActivity)
                            .load(it.avatar_url)
                            .placeholder(R.color.purple_500)
                            .into(imgProfileDetailSearch)

                        tvNameDetailSearch.text = it.name
                        tvUsernameDetailSearch.text = it.login
                        tvFollowingDetailSearch.text = it.following.toString()
                        tvFollowersDetailSearch.text = it.followers.toString()
                        tvRepositoryDetailSearch.text = it.public_repos.toString()
                        tvLocationDetailSearch.text = it.location
                        tvCompanyDetailSearch.text = it.company
                        tvLinkDetailSearch.text = it.blog
                    }
                }
            })
        }
    }

    private fun setViewPager() {

        val pagerAdapter = PagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPagerDetailSearch.adapter = pagerAdapter
            tabLayoutDetailSearch.setupWithViewPager(viewPagerDetailSearch)
        }
    }
}