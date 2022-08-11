package com.example.currencyconverter.presentation.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.ExchangeRateDialogBinding
import com.example.currencyconverter.presentation.adapter.ExchangeRatesAdapter

class ExchangeRateDialog(
    private val rates: List<String>,
    private val clickedRate: (String) -> Unit
) : DialogFragment() {
    private lateinit var binding: ExchangeRateDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExchangeRateDialogBinding.bind(
            LayoutInflater.from(context).inflate(
                R.layout.exchange_rate_dialog,
                container,
                false
            )
        )

        initIssueRecyclerView()

        return binding.root
    }

    private fun initIssueRecyclerView() {
        val exchangeRatesAdapter = ExchangeRatesAdapter {
            clickedRate(it)
            dismiss()
        }

        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = exchangeRatesAdapter
        }

        exchangeRatesAdapter.submitList(rates)
    }
}
