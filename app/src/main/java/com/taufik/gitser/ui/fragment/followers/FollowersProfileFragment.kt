package com.taufik.gitser.ui.fragment.followers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.gitser.R
import com.taufik.gitser.adapter.search.SearchAdapter
import com.taufik.gitser.data.viewmodel.following.FollowersViewModel
import com.taufik.gitser.databinding.FragmentFollowsBinding
import com.taufik.gitser.ui.activity.profile.ProfileActivity

class FollowersProfileFragment : Fragment(R.layout.fragment_follows) {

    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: SearchAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setArgument()

        _binding = FragmentFollowsBinding.bind(view)

        setAdapter()

        setViewModel()
    }

    private fun setArgument() {

        val argument = arguments
        username = argument?.getString(ProfileActivity.PROFILE_USERNAME).toString()
    }

    private fun setAdapter() {

        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()
    }

    private fun setViewModel() {

        binding.apply {
            with(rvFollows) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = adapter
            }
        }

        showLoading(true)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory())
            .get(FollowersViewModel::class.java)

        viewModel.setListOfFollowers(username)
        viewModel.getListOfFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setSearchUserList(it)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state) {
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