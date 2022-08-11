package com.example.currencyconverter.domain.model

data class ExchangeRates(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
