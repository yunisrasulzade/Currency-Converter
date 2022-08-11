package com.example.currencyconverter.data.api

import com.example.currencyconverter.data.api.Endpoints.CONVERT
import com.example.currencyconverter.data.api.Endpoints.LATEST
import com.example.currencyconverter.domain.model.ConvertResult
import com.example.currencyconverter.domain.model.ExchangeRates
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(LATEST)
    suspend fun fetchRates(): ExchangeRates

    @GET(CONVERT)
    suspend fun convert(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): ConvertResult
}
