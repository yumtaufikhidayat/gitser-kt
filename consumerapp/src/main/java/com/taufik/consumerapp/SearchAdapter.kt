package com.taufik.consumerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taufik.consumerapp.databinding.ItemUserBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private val listUsers = ArrayList<Search>()

    fun setSearchUserList(searches: ArrayList<Search>) {
        listUsers.clear()
        listUsers.addAll(searches)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(search: Search) {

            binding.apply {

                Glide.with(itemView)
                    .load(search.avatarUrl)
                    .placeholder(R.color.purple_500)
                    .into(imgUserProfile)

                tvUsernameProfile.text = search.login
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
    }

    override fun getItemCount(): Int = listUsers.size
}