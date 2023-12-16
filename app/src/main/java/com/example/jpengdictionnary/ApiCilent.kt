package com.example.jpengdictionnary

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://jisho.org/api/v1/search/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val jishoApiService: JishoApiService = retrofit.create(JishoApiService::class.java)
}