package com.example.currencyconverter.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<Throwable>()
    val loadingState: LiveData<Boolean>
        get() = loading

    fun getErrorLiveData(): LiveData<Throwable> {
        return errorLiveData
    }

    fun loading(isLoading: Boolean) {
        loading.value = isLoading
    }
}
