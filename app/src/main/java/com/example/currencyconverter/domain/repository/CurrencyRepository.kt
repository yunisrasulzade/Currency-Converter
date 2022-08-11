package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.domain.model.ConvertResult
import com.example.currencyconverter.domain.model.ExchangeRates

interface CurrencyRepository {
    suspend fun getLatestCurrencies(): ExchangeRates
    suspend fun convert(from: String, to: String, amount: Double): ConvertResult
}
