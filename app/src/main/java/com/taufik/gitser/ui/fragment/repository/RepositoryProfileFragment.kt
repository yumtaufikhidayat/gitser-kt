package com.taufik.gitser.ui.fragment.repository

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.gitser.R
import com.taufik.gitser.adapter.repository.RepositoryAdapter
import com.taufik.gitser.data.viewmodel.repository.RepositoryViewModel
import com.taufik.gitser.databinding.FragmentRepositoryBinding
import com.taufik.gitser.ui.activity.profile.ProfileActivity

class RepositoryProfileFragment : Fragment(R.layout.fragment_repository) {

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RepositoryViewModel
    private lateinit var repositoryAdapter: RepositoryAdapter
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
        repositoryAdapter = RepositoryAdapter()
        binding.apply {
            with(rvRepos) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = repositoryAdapter
            }
        }
    }

    private fun setViewModel() {

        showLoading(true)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory())
            .get(RepositoryViewModel::class.java
        )

        viewModel.setListOfRepository(username)
        viewModel.getListOfRepository().observe(viewLifecycleOwner) {
            if (it != null) {
                repositoryAdapter.setRepositoryList(it)
                showLoading(false)
            }
        }
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