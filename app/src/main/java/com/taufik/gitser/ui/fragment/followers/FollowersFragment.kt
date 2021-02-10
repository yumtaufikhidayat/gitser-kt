package com.taufik.gitser.ui.fragment.followers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.taufik.gitser.R
import com.taufik.gitser.databinding.FragmentFollowsBinding

class FollowersFragment : Fragment(R.layout.fragment_follows) {

    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowsBinding.bind(view)

    }
}