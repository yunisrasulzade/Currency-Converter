package com.example.currencyconverter

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

inline fun <reified T> T.TAG(): String = T::class.java.simpleName

@HiltAndroidApp
class App :Application()