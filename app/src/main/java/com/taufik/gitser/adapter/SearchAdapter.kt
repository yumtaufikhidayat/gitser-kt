package com.taufik.gitser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taufik.gitser.R
import com.taufik.gitser.data.model.search.Search
import com.taufik.gitser.databinding.ItemUserBinding
import es.dmoral.toasty.Toasty

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
        holder.bind(listUsers[position])
        holder.itemView.setOnClickListener {
            Toasty.success(holder.itemView.context, " ${listUsers[position].login}").show()
        }
    }

    override fun getItemCount(): Int = listUsers.size
}