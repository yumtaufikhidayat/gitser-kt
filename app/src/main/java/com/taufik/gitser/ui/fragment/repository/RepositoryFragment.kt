package com.taufik.gitser.ui.fragment.repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.gitser.adapter.repository.RepositoryAdapter
import com.taufik.gitser.data.viewmodel.repository.RepositoryViewModel
import com.taufik.gitser.databinding.FragmentRepositoryBinding
import com.taufik.gitser.ui.activity.detail.DetailSearchActivity

class RepositoryFragment : Fragment() {

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RepositoryViewModel
    private lateinit var repositoryAdapter: RepositoryAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setArguments()
        setAdapter()
        setData()
    }

    private fun setArguments() {
        val argument = arguments
        username = argument?.getString(DetailSearchActivity.EXTRA_DATA).toString()
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

    private fun setData() {
        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[RepositoryViewModel::class.java]
        viewModel.apply {
            setListOfRepository(username)
            getListOfRepository().observe(viewLifecycleOwner) {
                if (it != null) {
                    if (it.size != 0) {
                        repositoryAdapter.setRepositoryList(it)
                        showNoData(false)
                    } else {
                        showNoData(true)
                    }
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding.apply {
            if (isShow) {
                shimmerLoading.visibility = View.VISIBLE
                rvRepos.visibility = View.GONE
            } else {
                shimmerLoading.visibility = View.GONE
                rvRepos.visibility = View.VISIBLE
            }
        }
    }

    private fun showNoData(isShow: Boolean) {
        binding.apply {
            if (isShow) {
                viewNoRepositoryVisibility.visibility = View.VISIBLE
            } else {
                viewNoRepositoryVisibility.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}