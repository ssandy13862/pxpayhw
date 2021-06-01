package com.example.sandy_pxpay.utils

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix


object QRCodeUtil {

    fun encodeBitmap(qrCode: String, height: Int, width: Int): Bitmap{
        val bit = MultiFormatWriter().encode(qrCode, BarcodeFormat.QR_CODE, height, width)
        return matrixToBitmap(bit)
    }

    private fun matrixToBitmap(matrix: BitMatrix): Bitmap {
        val w = matrix.width
        val h = matrix.height
        val rawData = IntArray(w * h)

        for (i in 0 until w) {
            for (j in 0 until h) {
                //沒內容的顏色
                var color = Color.WHITE
                if (matrix.get(i, j)) {
                    //有內容的顏色
                    color = Color.BLACK
                }
                rawData[i + (j * w)] = color
            }
        }

        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(rawData, 0, w, 0, 0, w, h)
        return bitmap
    }

}