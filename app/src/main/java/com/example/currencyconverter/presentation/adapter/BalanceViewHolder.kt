package com.example.currencyconverter.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.databinding.ItemBalanceBinding

class BalanceViewHolder(
     val binding: ItemBalanceBinding
) : RecyclerView.ViewHolder(binding.root)

object BalanceDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}