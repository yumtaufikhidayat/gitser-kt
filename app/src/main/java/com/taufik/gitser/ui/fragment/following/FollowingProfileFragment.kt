package com.taufik.gitser.ui.fragment.following

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.gitser.R
import com.taufik.gitser.adapter.search.SearchAdapter
import com.taufik.gitser.data.viewmodel.followers.FollowingViewModel
import com.taufik.gitser.databinding.FragmentFollowsBinding
import com.taufik.gitser.ui.activity.profile.ProfileActivity

class FollowingProfileFragment : Fragment(R.layout.fragment_follows) {

    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowingViewModel
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowsBinding.bind(view)

        setArgument()
        setAdapter()
        setViewModel()
    }

    private fun setArgument() {
        val argument = arguments
        username = argument?.getString(ProfileActivity.PROFILE_USERNAME).toString()
    }

    private fun setAdapter() {
        searchAdapter = SearchAdapter()
        binding.apply {
            with(rvFollows) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = searchAdapter
            }
        }
    }

    private fun setViewModel() {
        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowingViewModel::class.java]

        viewModel.setListOfFollowing(username)
        viewModel.getListOfFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                searchAdapter.setSearchUserList(it)
                showLoading(false)
            }
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding.apply {
            if (isShow) {
                progressBarFollows.visibility = View.VISIBLE
            } else {
                progressBarFollows.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}