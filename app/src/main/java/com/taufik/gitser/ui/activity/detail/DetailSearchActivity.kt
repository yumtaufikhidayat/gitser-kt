package com.taufik.gitser.ui.activity.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.taufik.gitser.R
import com.taufik.gitser.adapter.PagerAdapter
import com.taufik.gitser.data.response.detail.DetailResponse
import com.taufik.gitser.data.response.search.Search
import com.taufik.gitser.data.viewmodel.detail.DetailViewModel
import com.taufik.gitser.databinding.ActivityDetailSearchBinding
import com.taufik.gitser.utils.Utils.isNetworkEnabled
import com.taufik.gitser.utils.Utils.loadImage
import com.taufik.gitser.utils.Utils.makeLinks
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSearchBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var bundle: Bundle
    private lateinit var dataParcel: Search
    private lateinit var data: DetailResponse
    private lateinit var link: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkConnectionEnabled()
    }

    private fun checkConnectionEnabled() {
        if (isNetworkEnabled(this)) {
            showNoNetworkConnection(false)
            getParcelableData()
            setBundleData()
            initActionBar()
            showDetailData()
            setViewPager()
            saveToFavorite()
        } else {
            showNoNetworkConnection(true)
            supportActionBar?.apply {
                title = ""
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    private fun showNoNetworkConnection(isShow: Boolean) {
        binding.apply {
            if (isShow) {
                layoutNoConnectionVisibility.visibility = View.VISIBLE
                layoutDetailVisibility.visibility = View.GONE
                layoutNoConnection.btnRetry.setOnClickListener {
                    checkConnectionEnabled()
                }
            } else {
                layoutNoConnectionVisibility.visibility = View.GONE
                layoutDetailVisibility.visibility = View.VISIBLE
            }
        }
    }

    private fun getParcelableData() {
        dataParcel = intent.getParcelableExtra<Search>(EXTRA_DATA) as Search
    }

    private fun setBundleData() {
        bundle = Bundle()
        bundle.putString(EXTRA_DATA, dataParcel.login)
    }

    private fun initActionBar() {
        supportActionBar?.apply {
            title = dataParcel.login
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding.apply {
            if (isShow) {
                shimmerLoadingDetail.visibility = View.VISIBLE
                layoutDetailVisibility.visibility = View.GONE
            } else {
                shimmerLoadingDetail.visibility = View.GONE
                layoutDetailVisibility.visibility = View.VISIBLE
            }
        }
    }

    private fun showDetailData() {
        showLoading(true)
        binding.apply {
            viewModel = ViewModelProvider(this@DetailSearchActivity)[DetailViewModel::class.java]
            viewModel.apply {
                setDetailSearch(dataParcel.login)
                getDetailSearch().observe(this@DetailSearchActivity) {
                    data = it
                    if (isNetworkEnabled(this@DetailSearchActivity)) {
                        if (it != null) {
                            imgProfileDetailSearch.loadImage(it.avatarUrl)
                            tvNameDetailSearch.text = it.name
                            tvUsernameDetailSearch.text = it.login
                            tvFollowingDetailSearch.text = it.following.toString()
                            tvFollowersDetailSearch.text = it.followers.toString()
                            tvRepositoryDetailSearch.text = it.publicRepos.toString()

                            if (it.location.isNullOrEmpty() || it.company.isNullOrEmpty() || it.blog.isNullOrEmpty()) {
                                link = ""
                                tvLocationDetailSearch.text = "-"
                                tvCompanyDetailSearch.text = "-"
                                tvLinkDetailSearch.text = "-"
                            } else {
                                link = it.blog
                                tvLocationDetailSearch.text = it.location
                                tvCompanyDetailSearch.text = it.company
                                tvLinkDetailSearch.text = link
                            }

                            tvLinkDetailSearch.makeLinks(Pair(link, View.OnClickListener {
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
                                    Log.e("errorLink", "showDetailData: ${e.localizedMessage}")
                                }
                            }))
                            showNoNetworkConnection(false)
                            showLoading(false)
                        } else {
                            if (isNetworkEnabled(this@DetailSearchActivity)) {
                                showNoNetworkConnection(false)
                                showLoading(false)
                            } else {
                                showNoNetworkConnection(true)
                                showLoading(false)
                            }
                        }
                    } else {
                        showNoNetworkConnection(true)
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun saveToFavorite() {
        binding.apply {
            var isChecked = false
            CoroutineScope(Dispatchers.IO).launch {
                val count = viewModel.checkUserFavorite(dataParcel.id)
                withContext(Dispatchers.Main) {
                    if (count != null) {
                        if (count > 0) {
                            toggleFavoriteDetailSearch.isChecked = true
                            isChecked = true
                        } else {
                            toggleFavoriteDetailSearch.isChecked = false
                            isChecked = false
                        }
                    }
                }
            }

            toggleFavoriteDetailSearch.setOnClickListener {
                isChecked = !isChecked
                if (isChecked) {
                    viewModel.addToFavorite(
                        dataParcel.id,
                        dataParcel.login,
                        dataParcel.avatarUrl,
                        dataParcel.type
                    )

                    Toasty.success(
                        this@DetailSearchActivity,
                        "Ditambahkan ke favorit",
                        Toast.LENGTH_SHORT,
                        true
                    ).show()
                } else {
                    viewModel.removeFromFavorite(dataParcel.id)
                    Toasty.success(
                        this@DetailSearchActivity,
                        "Dihapus dari favorit",
                        Toast.LENGTH_SHORT,
                        true
                    ).show()
                }

                toggleFavoriteDetailSearch.isChecked = isChecked
            }
        }
    }

    private fun setViewPager() {
        binding.apply {
            val pagerAdapter = PagerAdapter(this@DetailSearchActivity, bundle)
            viewPagerDetailSearch.adapter = pagerAdapter
            TabLayoutMediator(tabLayoutDetailSearch, viewPagerDetailSearch) { tabs, position ->
                tabs.text = resources.getString(tabTitles[position])
            }.attach()
        }
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

    companion object {
        const val EXTRA_DATA = "com.taufik.gitser.ui.activity.detail.EXTRA_DATA"

        @StringRes
        private val tabTitles = intArrayOf(
            R.string.tvFollowing,
            R.string.tvFollowers,
            R.string.tvRepository
        )
    }
}