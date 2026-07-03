package com.expenseflow.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.expenseflow.app.data.entity.CategoryEntity
import com.expenseflow.app.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val onItemClick: (CategoryEntity) -> Unit
) : ListAdapter<CategoryEntity, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryEntity) {
            binding.apply {
                tvCategoryName.text = category.name
                tvCategoryIcon.text = category.icon
                root.setOnClickListener { onItemClick(category) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryEntity>() {
        override fun areItemsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity) =
            oldItem == newItem
    }
}
