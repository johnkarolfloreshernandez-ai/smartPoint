package com.example.smartpoint.Modelo

import com.example.smartpoint.interfaceApi.ApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlin.getValue
import kotlin.jvm.java

object RetrofitCliente {

    const val BASE_URL = "https://somos4a.org/JohnFH/SmartPoint_api/"

    private val jsonConfig = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                jsonConfig.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
            .create(ApiService::class.java)
    }
}