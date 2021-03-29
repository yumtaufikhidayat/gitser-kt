package com.taufik.gitser.ui.activity.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.taufik.gitser.R
import com.taufik.gitser.adapter.profile.ProfilePagerAdapter
import com.taufik.gitser.data.model.detail.DetailResponse
import com.taufik.gitser.data.viewmodel.profile.ProfileViewModel
import com.taufik.gitser.databinding.ActivityProfileBinding
import com.taufik.gitser.ui.fragment.bottomsheet.BottomSheetProfileInfo
import es.dmoral.toasty.Toasty

class ProfileActivity : AppCompatActivity() {

    companion object{
        const val PROFILE_USERNAME = "yumtaufikhidayat"
    }

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var bundle: Bundle
    private lateinit var username: String
    private lateinit var data: DetailResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setInitData()

        initActionBar()

        setViewModel()

        setViewPager()
    }

    private fun setInitData() {

        username = PROFILE_USERNAME
    }

    private fun initActionBar() {

        supportActionBar?.title = "Profil"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setViewModel() {

        bundle = Bundle()
        bundle.putString(PROFILE_USERNAME, username)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )
            .get(ProfileViewModel::class.java)

        viewModel.setProfile(username)
        viewModel.getProfile().observe(this, {
            data = it
            if (it != null) {
                binding.apply {

                    imgProfile.loadImage(it.avatar_url)

                    tvProfileName.text = it.name
                    tvProfileUsername.text = it.login
                    tvFollowingProfile.text = it.following.toString()
                    tvFollowersProfile.text = it.followers.toString()
                    tvRepositoryProfile.text = it.public_repos.toString()
                    tvLocationProfile.text = it.location
                    tvCompanyProfile.text = it.company

                    val link = it.blog
                    tvLinkProfile.text = link

                    tvLinkProfile.makeLinks(Pair(it.blog, View.OnClickListener {
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

                            Log.e("errorLink", "setViewModel: ${e.localizedMessage}")
                        }
                    }))
                }
            }
        })
    }

    private fun setViewPager() {

        val profilePageAdapter = ProfilePagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPagerProfile.adapter = profilePageAdapter
            tabLayoutProfile.setupWithViewPager(viewPagerProfile)
        }
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
                .load(url)
                .apply(RequestOptions().override(500, 500))
                .centerCrop()
                .into(this)
    }

    private fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>){
        val spannableString = SpannableString(this.text)
        var startIndexOfLink = -1

        for (link in links) {
            val clickableSpan = object : ClickableSpan(){

                override fun updateDrawState(ds: TextPaint) {
                    ds.color = ds.linkColor
                    ds.isUnderlineText = false
                }

                override fun onClick(view: View) {
                    Selection.setSelection((view as TextView).text as Spannable, 0)
                    view.invalidate()
                    link.second.onClick(view)
                }
            }

            startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
            spannableString.setSpan(
                clickableSpan,
                startIndexOfLink,
                startIndexOfLink + link.first.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        this.movementMethod = LinkMovementMethod.getInstance()
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
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
                    val body = "Visit this awesome user \n${data.html_url}"
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
                    val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(data.html_url))
                    startActivity(Intent.createChooser(intentBrowser, "Open with:"))
                } catch (e: java.lang.Exception) {
                    Log.e("errorIntent", "onBindViewHolder: ${e.localizedMessage}")
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}