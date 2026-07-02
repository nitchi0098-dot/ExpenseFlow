package com.expenseflow.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.expenseflow.app.data.entity.ExpenseEntity
import com.expenseflow.app.databinding.ItemExpenseBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExpenseAdapter(
    private val onItemClick: (ExpenseEntity) -> Unit,
    private val onMarkPaid: (ExpenseEntity) -> Unit
) : ListAdapter<ExpenseEntity, ExpenseAdapter.ExpenseViewHolder>(ExpenseDiffCallback()) {

    inner class ExpenseViewHolder(private val binding: ItemExpenseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(expense: ExpenseEntity) {
            binding.apply {
                tvExpenseTitle.text = expense.title
                tvExpenseCategory.text = expense.category
                tvExpenseAmount.text = "₹${expense.amount}"
                
                val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                tvExpenseDate.text = dateFormat.format(Date(expense.date))
                
                tvExpenseStatus.text = if (expense.isPaid) "Paid" else "Pending"
                tvExpenseStatus.setTextColor(
                    if (expense.isPaid) 0xFF4CAF50.toInt() else 0xFFFF9800.toInt()
                )
                
                root.setOnClickListener { onItemClick(expense) }
                btnMarkPaid.setOnClickListener { onMarkPaid(expense) }
                btnMarkPaid.isEnabled = !expense.isPaid
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        return ExpenseViewHolder(
            ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class ExpenseDiffCallback : DiffUtil.ItemCallback<ExpenseEntity>() {
        override fun areItemsTheSame(oldItem: ExpenseEntity, newItem: ExpenseEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ExpenseEntity, newItem: ExpenseEntity) =
            oldItem == newItem
    }
}
