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
import com.taufik.gitser.ui.activity.detail.DetailSearchActivity

class FollowersFragment : Fragment(R.layout.fragment_follows) {

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
        username = argument?.getString(DetailSearchActivity.EXTRA_USERNAME).toString()
    }

    private fun setAdapter() {

        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()
    }

    private fun setViewModel() {

        binding.apply {
            rvFollows.setHasFixedSize(true)
            rvFollows.layoutManager = LinearLayoutManager(requireActivity())
            rvFollows.adapter = adapter
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