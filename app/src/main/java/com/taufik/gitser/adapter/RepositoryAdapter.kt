package com.taufik.gitser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taufik.gitser.data.model.repository.RepositoryResponse
import com.taufik.gitser.databinding.ItemRepositoryBinding

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.MyViewHolder>(){

    private val listRepository = ArrayList<RepositoryResponse>()

    fun setRepositoryList(repos: ArrayList<RepositoryResponse>) {
        listRepository.clear()
        listRepository.addAll(repos)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(private val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repository: RepositoryResponse) {

            binding.apply {
                tvRepositoryName.text = repository.name
                tvRepositoryDesc.text = repository.description
                tvRepositorySize.text = repository.size.toString()
                tvRepositoryLanguage.text = repository.language
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pos = listRepository[position]
        holder.bind(pos)
    }

    override fun getItemCount(): Int = listRepository.size
}