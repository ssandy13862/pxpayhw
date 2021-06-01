package com.example.sandy_pxpay.model

import android.os.Parcelable
import android.util.Log
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
class Message(
    var title: String = "",
    var msg: String = "",
    var timeStamp: Long? = null
) : Parcelable {

    constructor(jsonObject: JSONObject) : this() {
        title = jsonObject.getString("title")
        msg = jsonObject.getString("msg")
        timeStamp = jsonObject.getLong("ts")
    }

}

