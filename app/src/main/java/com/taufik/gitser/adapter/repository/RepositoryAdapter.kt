package com.taufik.gitser.adapter.repository

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taufik.gitser.data.model.repository.RepositoryResponse
import com.taufik.gitser.databinding.ItemRepositoryBinding
import java.lang.Exception
import kotlin.math.roundToInt

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
        holder.itemView.setOnClickListener{
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(pos.html_url))
                holder.itemView.context.startActivity(Intent.createChooser(intent, "Open with:"))
            } catch (e: Exception) {
                Log.e("errorIntent", "onBindViewHolder: ${e.localizedMessage}")
            }
        }
    }

    override fun getItemCount(): Int = listRepository.size
}