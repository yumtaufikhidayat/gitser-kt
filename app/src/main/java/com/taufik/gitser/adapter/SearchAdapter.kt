package com.taufik.gitser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taufik.gitser.R
import com.taufik.gitser.data.model.User
import com.taufik.gitser.databinding.ItemUserBinding
import es.dmoral.toasty.Toasty

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private val listUsers = ArrayList<User>()

    fun setSearchUserList(users: ArrayList<User>) {
        listUsers.clear()
        listUsers.addAll(users)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {

            binding.apply {

                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .placeholder(R.color.purple_500)
                    .into(imgUserProfile)

                tvUsernameProfile.text = user.login
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