package com.taufik.gitser.ui.activity.profile

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
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.taufik.gitser.R
import com.taufik.gitser.adapter.profile.ProfilePagerAdapter
import com.taufik.gitser.data.response.detail.DetailResponse
import com.taufik.gitser.data.viewmodel.profile.ProfileViewModel
import com.taufik.gitser.databinding.ActivityProfileBinding
import com.taufik.gitser.ui.fragment.bottomsheet.BottomSheetProfileInfo
import com.taufik.gitser.utils.Utils.isNetworkEnabled
import com.taufik.gitser.utils.Utils.loadImage
import com.taufik.gitser.utils.Utils.makeLinks
import es.dmoral.toasty.Toasty

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var bundle: Bundle
    private lateinit var username: String
    private lateinit var data: DetailResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkConnectionEnabled()
    }

    private fun checkConnectionEnabled() {
        if (isNetworkEnabled(this)) {
            showNoNetworkConnection(false)
            initActionBar()
            showDetailData()
            setViewPager()
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

    private fun initActionBar() {
        supportActionBar?.apply {
            title = "Profil"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding.apply {
            if (isShow) {
                shimmerLoadingProfile.visibility = View.VISIBLE
                layoutDetailVisibility.visibility = View.GONE
            } else {
                shimmerLoadingProfile.visibility = View.GONE
                layoutDetailVisibility.visibility = View.VISIBLE
            }
        }
    }

    private fun showDetailData() {
        showLoading(true)
        binding.apply {
            username = PROFILE_USERNAME
            bundle = Bundle()
            bundle.putString(PROFILE_USERNAME, username)

            viewModel = ViewModelProvider(this@ProfileActivity, ViewModelProvider.NewInstanceFactory())[ProfileViewModel::class.java]
            viewModel.apply {
                setProfile(username)
                getProfile().observe(this@ProfileActivity) {
                    data = it
                    if (isNetworkEnabled(this@ProfileActivity)) {
                        if (it != null) {
                            imgProfile.loadImage(it.avatarUrl)
                            tvProfileName.text = it.name
                            tvProfileUsername.text = it.login
                            tvFollowingProfile.text = it.following.toString()
                            tvFollowersProfile.text = it.followers.toString()
                            tvRepositoryProfile.text = it.publicRepos.toString()
                            tvProfileLocation.text = it.location
                            tvProfileCompany.text = it.company

                            val link = it.blog
                            tvProfileLink.text = link
                            tvProfileLink.makeLinks(Pair(it.blog, View.OnClickListener {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                                    startActivity(Intent.createChooser(intent, "Open with:"))
                                } catch (e: Exception) {
                                    Toasty.warning(
                                        this@ProfileActivity,
                                        "Silakan install browser terlebih dulu.",
                                        Toast.LENGTH_SHORT,
                                        true
                                    ).show()
                                    Log.e("errorLink", "showDetailData: ${e.localizedMessage}")
                                }
                            }))
                        }
                        showNoNetworkConnection(false)
                        showLoading(false)
                    } else {
                        if (isNetworkEnabled(this@ProfileActivity)) {
                            showNoNetworkConnection(false)
                            showLoading(false)
                        } else {
                            showNoNetworkConnection(true)
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun setViewPager() {
        binding.apply {
            val profilePageAdapter = ProfilePagerAdapter(this@ProfileActivity, bundle)
            viewPagerProfile.adapter = profilePageAdapter
            TabLayoutMediator(tabLayoutProfile, viewPagerProfile) {tabs, position ->
                tabs.text = resources.getString(tabTitles[position])
            }.attach()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()

            R.id.nav_detail_profile -> {
                val profileInfo = BottomSheetProfileInfo()
                profileInfo.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.BaseBottomSheetMenu
                )
                profileInfo.show(supportFragmentManager, "profileInfoBottomSheet")
            }

            R.id.nav_share_profile -> {
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

            R.id.nav_open_in_browser_profile -> {
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

    companion object{
        const val PROFILE_USERNAME = "yumtaufikhidayat"

        @StringRes
        private val tabTitles = intArrayOf(
            R.string.tvFollowing,
            R.string.tvFollowers,
            R.string.tvRepository
        )
    }
}