package com.example.currencyconverter.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.ItemBalanceBinding

class BalanceAdapter(
    private val balanceHashMap: HashMap<String, Double>,
    private val context: Context
) :
    ListAdapter<String, BalanceViewHolder>(BalanceDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        val binding = ItemBalanceBinding.inflate(LayoutInflater.from(parent.context))

        return BalanceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BalanceViewHolder, index: Int) {
        val currency = getItem(index)
        holder.binding.balance.text =
            context.getString(R.string.balance, currency, balanceHashMap[currency])
    }
}
