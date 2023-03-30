package com.prateek.mycbseguide.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.prateek.mycbseguide.databinding.ListItemBinding
import com.prateek.mycbseguide.models.Categories

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private lateinit var binding: ListItemBinding

    inner class CategoriesViewHolder(private val binding : ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Categories) {
            binding.category = category
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        binding = ListItemBinding
            .inflate(LayoutInflater
                .from(parent.context)
                , parent,
                false)
        return CategoriesViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) { holder.bind(differ.currentList[position]) }

    private val differCallBack = object : DiffUtil.ItemCallback<Categories>(){
        override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean = (newItem.id == oldItem.id)
        override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean = (newItem==oldItem)
    }

    val differ = AsyncListDiffer(this, differCallBack)
}