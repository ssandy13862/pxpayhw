package com.example.sandy_pxpay.model

import android.util.Log
import org.json.JSONObject

class Banner{

    var title: String
    var image: String
    var targetUrl: String

    constructor(jsonObject: JSONObject) {
        title = jsonObject.getString("title")
        image = jsonObject.getString("image")
        targetUrl = jsonObject.getString("target_url")
    }

}
