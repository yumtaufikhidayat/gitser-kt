package com.taufik.gitser.ui.activity.detail

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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.taufik.gitser.R
import com.taufik.gitser.adapter.PagerAdapter
import com.taufik.gitser.data.model.detail.DetailSearchResponse
import com.taufik.gitser.data.viewmodel.detail.DetailSearchViewModel
import com.taufik.gitser.databinding.ActivityDetailSearchBinding
import es.dmoral.toasty.Toasty

class DetailSearchActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "com.taufik.gitser.ui.activity.detail.EXTRA_USERNAME"
    }

    private lateinit var binding: ActivityDetailSearchBinding
    private lateinit var viewModel: DetailSearchViewModel
    private lateinit var bundle: Bundle
    private lateinit var username: String
    private lateinit var data: DetailSearchResponse

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
    }

    private fun initActionBar() {

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.title = username
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setViewModel() {

        bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory())
            .get(DetailSearchViewModel::class.java)

        viewModel.setDetailSearch(username)
        viewModel.getDetailSearch().observe(this, {
            data = it
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
    }

    private fun setViewPager() {

        val pagerAdapter = PagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPagerDetailSearch.adapter = pagerAdapter
            tabLayoutDetailSearch.setupWithViewPager(viewPagerDetailSearch)
        }
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
                clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        this.movementMethod = LinkMovementMethod.getInstance()
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()

            R.id.nav_share -> {

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

            R.id.nav_open_in_browser -> {
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