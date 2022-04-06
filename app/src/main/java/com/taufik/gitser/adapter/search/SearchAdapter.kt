package com.taufik.gitser.adapter.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taufik.gitser.data.response.search.Search
import com.taufik.gitser.databinding.ItemUserBinding
import com.taufik.gitser.ui.activity.detail.DetailSearchActivity
import com.taufik.gitser.utils.Utils.loadImage

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private val listUsers = ArrayList<Search>()

    fun setSearchUserList(searches: ArrayList<Search>) {
        listUsers.clear()
        listUsers.addAll(searches)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(private val binding: ItemUserBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(search: Search) {
            binding.apply {
                imgUserProfile.loadImage(search.avatarUrl)
                tvUsernameProfile.text = search.login
                tvProfileUrl.text = search.htmlUrl
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pos = listUsers[position]
        holder.bind(pos)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailSearchActivity::class.java).apply {
                putExtra(DetailSearchActivity.EXTRA_DATA, pos)
            }
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listUsers.size
}