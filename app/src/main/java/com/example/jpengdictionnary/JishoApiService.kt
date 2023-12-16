package com.example.jpengdictionnary

import retrofit2.http.GET
import retrofit2.http.Query

interface JishoApiService {
    @GET("words")
    suspend fun searchWords(@Query("keyword") keyword: String): ApiResponse
}