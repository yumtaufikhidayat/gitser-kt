package com.taufik.gitser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.taufik.gitser.ui.fragment.followers.FollowersFragment
import com.taufik.gitser.ui.fragment.following.FollowingFragment
import com.taufik.gitser.ui.fragment.repository.RepositoryFragment

class PagerAdapter(activity: AppCompatActivity, bundleData: Bundle) :
    FragmentStateAdapter (activity) {

    private var fragmentBundle: Bundle = bundleData

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment()
            1 -> fragment = FollowersFragment()
            2 -> fragment = RepositoryFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }
}