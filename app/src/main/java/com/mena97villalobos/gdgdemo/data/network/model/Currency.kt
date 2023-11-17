package com.mena97villalobos.gdgdemo.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true) data class CurrenciesResponse(val data: Map<String, Currency>)

@JsonClass(generateAdapter = true)
data class Currency(
    val symbol: String,
    val name: String,
    @Json(name = "symbol_native") val symbolNative: String,
    val code: String
) {
    val formattedName = "$name ($symbolNative)"
}
