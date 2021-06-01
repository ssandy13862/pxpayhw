package com.example.sandy_pxpay.utils

import com.example.sandy_pxpay.model.Banner
import com.example.sandy_pxpay.model.Message
import com.google.gson.JsonArray
import org.json.JSONObject

fun JsonArray.toMessageList(): ArrayList<Message> {
    val array = ArrayList<Message>()
    for (i in 0 until this.size()){
        array.add(Message(JSONObject(this[i].toString())))
    }
    return array
}

fun JsonArray.toBannerList(): ArrayList<Banner> {
    val array = ArrayList<Banner>()
    for (i in 0 until this.size()){
        array.add(Banner(JSONObject(this[i].toString())))
    }
    return array
}
