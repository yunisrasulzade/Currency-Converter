package com.example.currencyconverter.domain.useCase

import com.example.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class ExchangeRateUseCase @Inject constructor(private val repository: CurrencyRepository) {
    suspend fun execute() = repository.getLatestCurrencies()
}
