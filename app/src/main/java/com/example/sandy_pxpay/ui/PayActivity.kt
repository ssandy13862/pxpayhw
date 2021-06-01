package com.example.sandy_pxpay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sandy_pxpay.utils.QRCodeUtil
import com.example.sandy_pxpay.databinding.ActivityPayBinding

class PayActivity : AppCompatActivity() {

    private lateinit var bind: ActivityPayBinding

    companion object{
        const val ARG_QRCODE = "qrCode"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityPayBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setupView()
    }

    private fun setupView(){
        val qrCode = intent.getStringExtra(ARG_QRCODE)
        qrCode?.apply {
            bind.ivPayQrCode.setImageBitmap(QRCodeUtil.encodeBitmap(qrCode, 300, 300))
        }
    }
}