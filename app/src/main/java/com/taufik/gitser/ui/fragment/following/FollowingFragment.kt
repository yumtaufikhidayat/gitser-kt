package com.taufik.gitser.ui.fragment.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.gitser.R
import com.taufik.gitser.adapter.search.SearchAdapter
import com.taufik.gitser.data.viewmodel.following.FollowingViewModel
import com.taufik.gitser.databinding.FragmentFollowsBinding
import com.taufik.gitser.ui.activity.detail.DetailSearchActivity

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FollowingViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setArgument()
        initObserver()
        setAdapter()
        setData()
    }

    private fun setArgument() {
        val argument = arguments
        username = argument?.getString(DetailSearchActivity.EXTRA_DATA).toString()
    }

    private fun initObserver() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setAdapter() {
        searchAdapter = SearchAdapter()
        binding.apply {
            with(rvFollows) {setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = searchAdapter
            }
        }
    }

    private fun setData() {
        viewModel.apply {
            setListOfFollowing(username)
            listOfFollowing.observe(viewLifecycleOwner) {
                if (it != null) {
                    if (it.size != 0) {
                        searchAdapter.submitList(it)
                        showNoData(false)
                    } else {
                        showNoData(true)
                    }
                }
            }
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding.apply {
            if (isShow) {
                shimmerLoading.visibility = View.VISIBLE
                rvFollows.visibility = View.GONE
            } else {
                shimmerLoading.visibility = View.GONE
                rvFollows.visibility = View.VISIBLE
            }
        }
    }

    private fun showNoData(isShow: Boolean) {
        binding.apply {
            if (isShow) {
                viewNoDataVisibility.visibility = View.VISIBLE
                layoutNoData.tvNoDataDesc.text = getString(R.string.tvNoFollowing)
            } else {
                viewNoDataVisibility.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}