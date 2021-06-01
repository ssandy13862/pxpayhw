package com.example.sandy_pxpay.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitManager {

    private lateinit var apiService: APIService

    fun init(){

        // Interceptor
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        // Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        apiService = retrofit.create(APIService::class.java)
    }

    fun getAPI(): APIService {
        return apiService
    }

}