package com.taufik.gitser.adapter.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taufik.gitser.data.response.search.Search
import com.taufik.gitser.databinding.ItemUserBinding
import com.taufik.gitser.ui.activity.detail.DetailSearchActivity
import com.taufik.gitser.utils.Utils.loadImage

class SearchAdapter: ListAdapter<Search, SearchAdapter.SearchViewHolder>(SearchDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) = holder.bind(getItem(position))

    inner class SearchViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(search: Search) = with(binding) {
            imgUserProfile.loadImage(search.avatarUrl)
            tvUsernameProfile.text = search.login
            tvProfileType.text = search.type

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailSearchActivity::class.java).apply {
                    putExtra(DetailSearchActivity.EXTRA_DATA, search)
                }
                it.context.startActivity(intent)
            }
        }
    }

    object SearchDiffCallback : DiffUtil.ItemCallback<Search>() {
        override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean = oldItem == newItem
    }
}