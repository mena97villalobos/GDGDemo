package com.mena97villalobos.gdgdemo.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExchangeRateResponse(val data: Map<String, ExchangeRateValues>)

@JsonClass(generateAdapter = true)
data class ExchangeRateValues(val code: String, val value: Double)
