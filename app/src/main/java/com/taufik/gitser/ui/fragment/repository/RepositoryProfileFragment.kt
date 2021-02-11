package com.taufik.gitser.ui.fragment.repository

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.gitser.R
import com.taufik.gitser.adapter.RepositoryAdapter
import com.taufik.gitser.data.viewmodel.repository.RepositoryViewModel
import com.taufik.gitser.databinding.FragmentRepositoryBinding
import com.taufik.gitser.ui.activity.profile.ProfileActivity

class RepositoryProfileFragment : Fragment(R.layout.fragment_repository) {

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RepositoryViewModel
    private lateinit var adapter: RepositoryAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setArguments()

        _binding = FragmentRepositoryBinding.bind(view)

        setAdapter()

        setViewModel()
    }

    private fun setArguments() {

        val argument = arguments
        username = argument?.getString(ProfileActivity.PROFILE_USERNAME).toString()
    }

    private fun setAdapter() {

        adapter = RepositoryAdapter()
        adapter.notifyDataSetChanged()
    }

    private fun setViewModel() {

        binding.apply {
            rvRepos.setHasFixedSize(true)
            rvRepos.layoutManager = LinearLayoutManager(requireActivity())
            rvRepos.adapter = adapter
        }

        showLoading(true)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory())
            .get(RepositoryViewModel::class.java
        )

        viewModel.setListOfRepository(username)
        viewModel.getListOfRepository().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setRepositoryList(it)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {

        if (state) {
            binding.progressBarFollows.visibility = View.VISIBLE
        } else {
            binding.progressBarFollows.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}