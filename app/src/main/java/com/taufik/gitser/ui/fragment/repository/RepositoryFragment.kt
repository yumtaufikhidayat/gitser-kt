package com.taufik.gitser.ui.fragment.repository

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.taufik.gitser.R
import com.taufik.gitser.databinding.FragmentRepositoryBinding

class RepositoryFragment : Fragment(R.layout.fragment_repository) {

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRepositoryBinding.bind(view)
        
    }
}