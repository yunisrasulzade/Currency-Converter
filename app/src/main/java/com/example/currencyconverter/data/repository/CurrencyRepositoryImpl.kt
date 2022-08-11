package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.api.ApiService
import com.example.currencyconverter.domain.model.ConvertResult
import com.example.currencyconverter.domain.model.ExchangeRates
import com.example.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    CurrencyRepository {

    override suspend fun getLatestCurrencies(): ExchangeRates {
        return apiService.fetchRates()
    }

    override suspend fun convert(from: String, to: String, amount: Double): ConvertResult {
        return apiService.convert(from, to, amount)
    }
}
