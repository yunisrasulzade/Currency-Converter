package com.example.currencyconverter.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.R
import com.example.currencyconverter.TAG
import com.example.currencyconverter.databinding.FragmentExchangeBinding
import com.example.currencyconverter.domain.enums.ExchangeType
import com.example.currencyconverter.presentation.adapter.BalanceAdapter
import com.example.currencyconverter.presentation.base.BaseFragment
import com.example.currencyconverter.presentation.ui.dialog.ExchangeRateDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeFragment :
    BaseFragment<FragmentExchangeBinding, ExchangeViewModel>() {
    private lateinit var viewModel: ExchangeViewModel
    private var sellCurrency = "EUR"
    private var buyCurrency = "USD"
    private lateinit var balanceAdapter: BalanceAdapter
    private var currencyList = ArrayList<String>()
    private var balanceList = HashMap<String, Double>()

    override fun getLayoutId(): Int = R.layout.fragment_exchange

    override fun getViewModel(): ExchangeViewModel {
        viewModel = ViewModelProvider(this)[ExchangeViewModel::class.java]
        return viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sellTextView.setOnClickListener {
            showCurrencyDialog(balanceList.keys.toList(), ExchangeType.SELL)
        }

        binding.buyTextView.setOnClickListener {
            showCurrencyDialog(currencyList, ExchangeType.BUY)
        }

        binding.convert.setOnClickListener {
            viewModel.convert(
                sellCurrency,
                buyCurrency,
                binding.sellEditText.text.toString().toDouble()
            )
        }

        lifecycleScope.launchWhenCreated {
            viewModel.mainActivityState.collect {
                Log.d(TAG(), "onCreate: $it")
                when (it) {
                    is ExchangeState.ExchangeListState -> {
                        currencyList.clear()
                        currencyList.addAll(it.currencies)
                    }
                    is ExchangeState.ConversionResult -> binding.buyEditText.setText(it.result.toString())
                    is ExchangeState.BalanceList -> {
                        balanceList = it.balanceList
                        initBalanceRecyclerView(balanceList)
                    }
                    is ExchangeState.NotEnoughBalance -> baseActivity.showMessage(getString(R.string.balance_warning))
                }
            }
        }
    }

    private fun initBalanceRecyclerView(balanceMap: HashMap<String, Double>) {
        balanceAdapter = BalanceAdapter(balanceMap, requireContext())

        binding.balanceRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = balanceAdapter
        }

        balanceAdapter.submitList(balanceMap.keys.toList())
    }

    private fun showCurrencyDialog(list: List<String>, type: ExchangeType) {
        val dialog = ExchangeRateDialog(list) {
            if (type == ExchangeType.SELL) {
                binding.sellTextView.text = it
                sellCurrency = it
            } else {
                binding.buyTextView.text = it
                buyCurrency = it
            }
        }
        dialog.show(childFragmentManager, "dialog")
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentExchangeBinding
        get() = FragmentExchangeBinding::inflate

}
