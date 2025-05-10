package com.example.tena.data.network

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:5000"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
