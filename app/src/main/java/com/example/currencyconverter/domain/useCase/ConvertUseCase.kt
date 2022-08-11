package com.example.currencyconverter.domain.useCase

import com.example.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class ConvertUseCase @Inject constructor(private val repository: CurrencyRepository) {
    suspend fun execute(from: String, to: String, amount: Double) =
        repository.convert(from, to, amount)
}
