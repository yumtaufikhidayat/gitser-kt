package com.taufik.gitser.ui.activity.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.*
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.taufik.gitser.R
import com.taufik.gitser.adapter.PagerAdapter
import com.taufik.gitser.data.model.detail.DetailResponse
import com.taufik.gitser.data.viewmodel.detail.DetailViewModel
import com.taufik.gitser.databinding.ActivityDetailSearchBinding
import com.taufik.gitser.utils.Utils.Companion.makeLinks
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

class DetailSearchActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "com.taufik.gitser.ui.activity.detail.EXTRA_USERNAME"
        const val EXTRA_ID = "com.taufik.gitser.ui.activity.detail.EXTRA_ID"
        const val EXTRA_AVATAR = "com.taufik.gitser.ui.activity.detail.EXTRA_AVATAR"
    }

    private lateinit var binding: ActivityDetailSearchBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var bundle: Bundle
    private lateinit var username: String
    private var id by Delegates.notNull<Int>()
    private lateinit var avatarUrl: String
    private lateinit var data: DetailResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setParcelableData()

        initActionBar()

        setViewModel()

        setViewPager()
    }

    private fun setParcelableData() {

        username = intent.getStringExtra(EXTRA_USERNAME).toString()
        id = intent.getIntExtra(EXTRA_ID, 0)
        avatarUrl = intent.getStringExtra(EXTRA_AVATAR).toString()
    }

    private fun initActionBar() {
        supportActionBar?.title = username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setViewModel() {

        bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        viewModel.setDetailSearch(username)
        viewModel.getDetailSearch().observe(this, {
            data = it
            if (it != null) {
                binding.apply {

                    imgProfileDetailSearch.loadImage(it.avatarUrl)

                    tvNameDetailSearch.text = it.name
                    tvUsernameDetailSearch.text = it.login
                    tvFollowingDetailSearch.text = it.following.toString()
                    tvFollowersDetailSearch.text = it.followers.toString()
                    tvRepositoryDetailSearch.text = it.publicRepos.toString()
                    tvLocationDetailSearch.text = it.location
                    tvCompanyDetailSearch.text = it.company

                    val link = it.blog
                    tvLinkDetailSearch.text = link

                    tvLinkDetailSearch.makeLinks(Pair(it.blog, View.OnClickListener {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                            startActivity(Intent.createChooser(intent, "Open with:"))
                        } catch (e: Exception) {
                            Toasty.warning(
                                this@DetailSearchActivity,
                                "Silakan install browser terlebih dulu.",
                                Toast.LENGTH_SHORT,
                                true
                            ).show()

                            Log.e("errorLink", "setViewModel: ${e.localizedMessage}" )
                        }
                    }))
                }
            }
        })

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUserFavorite(id)
            withContext(Dispatchers.Main){
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFavoriteDetailSearch.isChecked = true
                        isChecked = true
                    } else {
                        binding.toggleFavoriteDetailSearch.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.toggleFavoriteDetailSearch.setOnClickListener{
            isChecked = !isChecked
            if (isChecked) {
                viewModel.addToFavorite(id, username, avatarUrl)
                Toasty.success(this@DetailSearchActivity, "Ditambahkan ke favorit", Toast.LENGTH_SHORT, true).show()
            } else {
                viewModel.removeFromFavorite(id)
                Toasty.success(this@DetailSearchActivity, "Dihapus dari favorit", Toast.LENGTH_SHORT, true).show()
            }

            binding.toggleFavoriteDetailSearch.isChecked = isChecked
        }
    }

    private fun setViewPager() {

        val pagerAdapter = PagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPagerDetailSearch.adapter = pagerAdapter
            tabLayoutDetailSearch.setupWithViewPager(viewPagerDetailSearch)
        }
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
                .load(url)
                .apply(RequestOptions().override(500, 500))
                .centerCrop()
                .into(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()

            R.id.nav_share -> {

                try {

                    val body = "Visit this awesome user \n${data.htmlUrl}"

                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, body)
                    startActivity(Intent.createChooser(shareIntent, "Share with:"))
                } catch (e: Exception) {
                    Log.e("shareFailed", "onOptionsItemSelected: ${e.localizedMessage}")
                }
            }

            R.id.nav_open_in_browser -> {
                try {
                    val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(data.htmlUrl))
                    startActivity(Intent.createChooser(intentBrowser, "Open with:"))
                } catch (e: java.lang.Exception) {
                    Log.e("errorIntent", "onBindViewHolder: ${e.localizedMessage}")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}