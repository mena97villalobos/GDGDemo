package com.mena97villalobos.gdgdemo.data.network.interceptor

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mena97villalobos.gdgdemo.BuildConfig
import java.io.BufferedReader
import java.io.InputStreamReader
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor(private val context: Context) : Interceptor {

    companion object {
        private const val MOCK_DIRECTORY_PREFIX = "mock/"
    }

    private val mockConfig = readMockConfig()

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()
        val method = chain.request().method

        val request = chain.request()

        if (BuildConfig.MOCK_CONFIG_ENABLED) {
            val config = getMockConfiguration(method, uri)
            return if (config != null) {
                val responseString =
                    readFile("$MOCK_DIRECTORY_PREFIX${config.fileName}${config.fileExt}")
                Response.Builder()
                    .code(config.statusCode)
                    .message(responseString)
                    .request(request)
                    .protocol(Protocol.HTTP_2)
                    .body(
                        responseString
                            .toByteArray()
                            .toResponseBody(config.contentType.toMediaType()))
                    .addHeader("content-type", config.contentType)
                    .build()
            } else {
                chain.proceed(request)
            }
        }
        return chain.proceed(request)
    }

    private fun getMockConfiguration(method: String, uri: String) =
        mockConfig.firstOrNull {
            it.pathPattern.toRegex().matches(uri) &&
                it.method.equals(method, ignoreCase = true) &&
                it.isEnabled
        }

    private fun readMockConfig(): List<MockConfig> {
        val fileJson = readFile("MockDataConfiguration.json")
        return Gson().fromJson(fileJson, object : TypeToken<List<MockConfig>>() {})
    }

    private fun readFile(fileName: String): String {
        val buf = BufferedReader(InputStreamReader(context.assets.open(fileName)))
        var line = buf.readLine()
        val sb = StringBuilder()
        while (line != null) {
            sb.append(line).append("\n")
            line = buf.readLine()
        }
        return sb.toString()
    }
}

data class MockConfig(
    val fileName: String,
    val pathPattern: String,
    val method: String,
    val statusCode: Int,
    val isEnabled: Boolean,
    val fileExt: String,
    val contentType: String
)
