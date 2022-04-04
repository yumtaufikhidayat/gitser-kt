package com.taufik.gitser.adapter.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.taufik.gitser.ui.fragment.followers.FollowersProfileFragment
import com.taufik.gitser.ui.fragment.following.FollowingProfileFragment
import com.taufik.gitser.ui.fragment.repository.RepositoryProfileFragment

class ProfilePagerAdapter(activity: AppCompatActivity, bundleData: Bundle):
    FragmentStateAdapter(activity) {

    private var fragmentBundle: Bundle = bundleData

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingProfileFragment()
            1 -> fragment = FollowersProfileFragment()
            2 -> fragment = RepositoryProfileFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }
}