package com.example.sandy_pxpay.api

import com.google.gson.JsonObject

data class APIResponse(val status_code: String,
                       val result: JsonObject)

