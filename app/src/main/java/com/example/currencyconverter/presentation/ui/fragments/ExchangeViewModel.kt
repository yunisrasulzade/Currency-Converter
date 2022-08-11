package com.example.currencyconverter.presentation.ui.fragments

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.TAG
import com.example.currencyconverter.domain.useCase.ConvertUseCase
import com.example.currencyconverter.domain.useCase.ExchangeRateUseCase
import com.example.currencyconverter.presentation.base.BaseViewModel
import com.example.currencyconverter.presentation.util.Utils.ifNonNull
import com.example.currencyconverter.presentation.util.Utils.ifNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val exchangeRateUseCase: ExchangeRateUseCase,
    private val convertUseCase: ConvertUseCase
) :
    BaseViewModel() {

    val mainActivityState = MutableSharedFlow<ExchangeState>()

    private val balanceCurrencyList = HashMap<String, Double>()
    private val exchangeRatesList = ArrayList<String>()

    init {
        balanceCurrencyList["EUR"] = 1000.00
        viewModelScope.launch(Dispatchers.Main) {
            mainActivityState.emit(ExchangeState.BalanceList(balanceCurrencyList))
        }

        fetchExchangeRates()
    }

    fun convert(from: String, to: String, amount: Double) {
        Log.d(TAG(), "convert: $to")

        viewModelScope.launch {
            flow { emit(convertUseCase.execute(from, to, amount)) }
                .flowOn(Dispatchers.IO)
                .onStart { loading(true) }
                .onCompletion { loading(false) }
                .catch {
                    errorLiveData.value = it
                }
                .collectLatest { conversionResult ->
                    val sellCurrency = balanceCurrencyList.entries.find { pair ->
                        pair.key == from
                    }
                    val buyCurrency = balanceCurrencyList.entries.find { pair ->
                        pair.key == to
                    }

                    sellCurrency?.value?.let { sell ->
                        if (amount <= sell) {
                            balanceCurrencyList[from] = sell - amount

                            buyCurrency.ifNull {
                                balanceCurrencyList[to] = conversionResult.result
                            }.ifNonNull {
                                balanceCurrencyList[to] = it.value + conversionResult.result
                            }

                            mainActivityState.emit(ExchangeState.BalanceList(balanceCurrencyList))
                            mainActivityState.emit(ExchangeState.ConversionResult(conversionResult.result))
                            Log.d(TAG(), "fetchExchangeRates: ${conversionResult.result}")
                        } else {
                            mainActivityState.emit(ExchangeState.NotEnoughBalance)
                        }
                    }
                }
        }
    }

    private fun fetchExchangeRates() {
        viewModelScope.launch {
            while (isActive) {
                flow { emit(exchangeRateUseCase.execute()) }
                    .flowOn(Dispatchers.IO)
                    .catch {
                        errorLiveData.value = it
                    }
                    .collectLatest {
                        Log.d(TAG(), "fetchExchangeRates: ${it.base} ${it.rates.size}")

                        it.rates.forEach { map ->

                            exchangeRatesList.add(map.key)

                            Log.d(TAG(), "fetchExchangeRates: $it")
                        }

                        mainActivityState.emit(ExchangeState.ExchangeListState(exchangeRatesList))
                    }
                delay(5000)
            }
        }
    }
}
