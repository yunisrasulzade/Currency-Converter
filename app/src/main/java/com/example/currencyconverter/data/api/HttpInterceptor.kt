package com.example.currencyconverter.data.api

import android.content.Context
import com.example.currencyconverter.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HttpInterceptor @Inject constructor(
    private val context: Context
) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        request.addHeader("apikey", BuildConfig.API_KEY)

        return chain.proceed(request.build())
    }
}
