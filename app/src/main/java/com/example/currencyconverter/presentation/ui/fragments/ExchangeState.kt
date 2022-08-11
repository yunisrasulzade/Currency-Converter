package com.example.currencyconverter.presentation.ui.fragments

sealed class ExchangeState {

    data class ExchangeListState(val currencies: ArrayList<String>) : ExchangeState()
    data class ConversionResult(val result: Double) : ExchangeState()
    data class BalanceList(val balanceList: HashMap<String, Double>) : ExchangeState()
    object NotEnoughBalance : ExchangeState()
}
