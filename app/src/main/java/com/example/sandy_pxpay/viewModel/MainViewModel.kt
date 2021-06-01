package com.example.sandy_pxpay.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sandy_pxpay.api.APIResponse
import com.example.sandy_pxpay.api.APIService
import com.example.sandy_pxpay.api.RetrofitManager
import com.example.sandy_pxpay.model.Banner
import com.example.sandy_pxpay.model.Message
import com.example.sandy_pxpay.utils.Dispatcher
import com.example.sandy_pxpay.utils.toBannerList
import com.example.sandy_pxpay.utils.toMessageList
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {

    private lateinit var apiService: APIService

    private val _qrCode = MutableLiveData<String>()
    val qrCode: LiveData<String> get() = _qrCode

    private val _msgList = MutableLiveData<ArrayList<Message>>()
    val msgList: LiveData<ArrayList<Message>> get() = _msgList

    private val _bannerList = MutableLiveData<ArrayList<Banner>>()
    val bannerList: LiveData<ArrayList<Banner>> get() = _bannerList

    private val _errorCode = MutableLiveData<Boolean>()
    val errorCode: LiveData<Boolean> get() = _errorCode

    companion object {
        const val RES_QRCODE = "qr_code"
        const val RES_MSGS = "messages"
        const val RES_BANNERS = "banners"
    }

    fun init() {
        RetrofitManager.init()
        apiService = RetrofitManager.getAPI()
    }

    fun getQRCode(appVersion: String) {
        Dispatcher.async{
            val call = apiService.getQRCode(appVersion)
            call.enqueue(object : retrofit2.Callback<APIResponse> {
                override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                    Log.d("LogResponse", response.body().toString())
                    Dispatcher.main{
                        when (response.body()?.status_code) {
                            "0" -> {
                                val result = response.body()?.result
                                val resQRCode = result?.get(RES_QRCODE).toString()
                                _qrCode.value = resQRCode
                            }
                            "9999" -> {
                                _errorCode.value = true
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                    Log.d("LogFailure", "${t.message}")
                    Dispatcher.main{ _errorCode.value = true }
                }
            })
        }
    }

    fun getMessages(appVersion: String) {
        Dispatcher.async {
            val call = apiService.getMessages(appVersion)
            call.enqueue(object : retrofit2.Callback<APIResponse> {
                override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                    Log.d("LogResponse", response.body().toString())
                    Dispatcher.main {
                        when (response.body()?.status_code) {
                            "0" -> {
                                val result = response.body()?.result
                                val resMsg = result?.get(RES_MSGS)?.asJsonArray
                                resMsg?.apply {
                                    _msgList.value = this.toMessageList()
                                }
                            }
                            "9999" -> {
                                _errorCode.value = true
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                    Log.d("LogFailure", "${t.message}")
                    Dispatcher.main{ _errorCode.value = true }
                }
            })
        }
    }

    fun getBanners(appVersion: String) {
        Dispatcher.async {
            val call = apiService.getBanners(appVersion)
            call.enqueue(object : retrofit2.Callback<APIResponse> {
                override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                    Log.d("LogResponse", response.body().toString())
                    Dispatcher.main {
                        when (response.body()?.status_code) {
                            "0" -> {
                                val result = response.body()?.result
                                val resMsg = result?.get(RES_BANNERS)?.asJsonArray
                                resMsg?.apply {
                                    _bannerList.value = this.toBannerList()
                                }
                            }
                            "9999" -> {
                                //非使用者操作，不顯示錯誤提示
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                    Log.d("LogFailure", "${t.message}")
                    Dispatcher.main{ _errorCode.value = true }
                }
            })
        }
    }


}


