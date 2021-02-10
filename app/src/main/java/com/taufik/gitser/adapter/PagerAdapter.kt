package com.taufik.gitser.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.taufik.gitser.R
import com.taufik.gitser.ui.fragment.followers.FollowersFragment
import com.taufik.gitser.ui.fragment.following.FollowingFragment
import com.taufik.gitser.ui.fragment.repository.RepositoryFragment

class PagerAdapter(private val context: Context, fragmentManager: FragmentManager, bundleData: Bundle)
    : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundle: Bundle = bundleData

    @StringRes
    private val tabsTitle = intArrayOf(R.string.tvFollowing, R.string.tvFollowers, R.string.tvRepository)

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment()
            1 -> fragment = FollowersFragment()
            2 -> fragment = RepositoryFragment()
        }

        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(tabsTitle[position])
    }
}