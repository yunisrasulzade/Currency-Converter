package com.example.currencyconverter.presentation.util

import android.content.Context
import android.util.Log
import com.example.currencyconverter.R
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.io.IOException

class ErrorHandler(private val context: Context) {
    @Throws(IOException::class)
    fun handleError(throwable: Throwable): String {
        throwable.printStackTrace()

        when (throwable) {
            is HttpException -> {

                Log.d("handleError", "handleError: ${throwable.code()}")

                val json = throwable.response()?.errorBody()!!.string()
                val error = convert(json)

                error?.message?.let {
                    return it
                }
            }
        }

        return context.getString(R.string.general_error)
    }

    fun convert(json: String): Error? {
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<Error> =
            moshi.adapter(Error::class.java)

        return jsonAdapter.fromJson(json)
    }
}
