package com.example.sandy_pxpay.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface APIService {

    @GET("v3/8c29aeec-3ab4-4ac1-9b2e-e99652dbd155")
    fun getQRCode(@Header("app_version")appVersion: String): Call<APIResponse>

    @GET("/v3/0f0488e1-e532-45e5-8033-bef5904359fe")
    fun getMessages(@Header("app_version")appVersion: String): Call<APIResponse>

    @GET("/v3/f6733f2d-82fc-43e7-b19d-d8381f0ab91e")
    fun getBanners(@Header("app_version")appVersion: String): Call<APIResponse>
}
