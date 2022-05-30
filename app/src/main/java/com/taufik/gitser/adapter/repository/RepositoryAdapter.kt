package com.taufik.gitser.adapter.repository

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taufik.gitser.data.response.detail.RepositoryResponse
import com.taufik.gitser.databinding.ItemRepositoryBinding
import kotlin.math.roundToInt

class RepositoryAdapter : ListAdapter<RepositoryResponse, RepositoryAdapter.RepositoryViewHolder>(RepositoryDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: RepositoryViewHolder,
        position: Int
    ) = holder.bind(getItem(position))

    inner class RepositoryViewHolder(private val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: RepositoryResponse) = with(binding){
            tvRepositoryName.text = repository.name
            tvRepositoryDesc.text = repository.description
            tvRepositorySize.text = repository.size.toString()
            tvRepositoryLanguage.text = repository.language

            val repoSize = repository.size
            tvRepositorySize.text = repoSize.toString()

            val sizeStr = tvRepositorySize.text.toString().trim()
            val sizeInt = sizeStr.toInt()
            val sizeDouble = sizeInt / 1000.toFloat()
            val sizeIntNew = sizeDouble.roundToInt()

            if (sizeIntNew < 1) {
                val sizeDoubleKb = (sizeDouble * 1000).roundToInt()
                val sizeIntKb = sizeDoubleKb.toDouble().roundToInt()
                tvRepositorySize.text = String.format("%s KB", sizeIntKb)
            } else {
                tvRepositorySize.text = String.format("%s MB", sizeIntNew)
            }

            itemView.setOnClickListener {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repository.html_url))
                    itemView.context.startActivity(Intent.createChooser(intent, "Open with:"))
                } catch (e: Exception) {
                    Log.e("errorIntent", "onBindViewHolder: ${e.localizedMessage}")
                }
            }
        }
    }

    companion object {
        object RepositoryDiffCallback : DiffUtil.ItemCallback<RepositoryResponse>() {
            override fun areItemsTheSame(
                oldItem: RepositoryResponse,
                newItem: RepositoryResponse
            ): Boolean = oldItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: RepositoryResponse,
                newItem: RepositoryResponse
            ): Boolean = oldItem == newItem
        }
    }
}