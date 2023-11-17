package com.mena97villalobos.gdgdemo.data.network

import com.mena97villalobos.gdgdemo.data.network.model.Currency

interface ICurrencyService {

    suspend fun getAvailableCurrencies(): List<Currency>

}
