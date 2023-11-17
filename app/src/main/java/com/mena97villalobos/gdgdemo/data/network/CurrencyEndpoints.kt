package com.mena97villalobos.gdgdemo.data.network

import com.mena97villalobos.gdgdemo.BuildConfig
import com.mena97villalobos.gdgdemo.data.network.model.CurrenciesResponse
import com.mena97villalobos.gdgdemo.data.network.model.ExchangeRateResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CurrencyEndpoints {

    @Headers("$API_KEY_HEADER: ${BuildConfig.CURRENCY_API_KEY}")
    @GET(CURRENCY_AVAILABLE)
    suspend fun fetchAvailableCurrencies(): CurrenciesResponse

    @Headers("$API_KEY_HEADER: ${BuildConfig.CURRENCY_API_KEY}")
    @GET(CURRENCY_LATEST_EXCHANGE)
    suspend fun fetchLatestExchangeRate(
        @Query(BASE_CURRENCY_QUERY) baseCurrencyCode: String,
        @Query(CURRENCIES_QUERY) targetCurrencyCode: String
    ): ExchangeRateResponse
}
