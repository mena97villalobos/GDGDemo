package com.mena97villalobos.gdgdemo.data.network

import android.util.Log
import com.mena97villalobos.gdgdemo.data.network.model.Currency
import retrofit2.HttpException

class CurrencyService(private val currencyEndpoints: CurrencyEndpoints) : ICurrencyService {

    private val comparator =
        Comparator<Currency> { o1, o2 ->
            return@Comparator if (o1.code == "CRC" || o1.code == "USD") {
                1
            } else if (o2.code == "CRC" || o2.code == "USD") {
                -1
            } else {
                o1.name.compareTo(o2.name)
            }
        }

    override suspend fun getAvailableCurrencies(): List<Currency> {
        try {
            val response = currencyEndpoints.fetchAvailableCurrencies()
            return response.data.values.sortedWith(comparator).toList()
        } catch (e: HttpException) {
            Log.e("ERROR", e.stackTraceToString())
        }
        return emptyList()
    }
}
