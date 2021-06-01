package com.example.sandy_pxpay.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.*

object Dispatcher {

    private val processors = Runtime.getRuntime().availableProcessors()
    private val mMultiThreadExecutor: Executor = Executors.newCachedThreadPool()
    private var uiHandler: Handler? = null

    private val threadPoolExecutor: ThreadPoolExecutor = ThreadPoolExecutor(
        processors / 2,          //核心thread
        processors,                           //當BlockingQueue滿時，最多可開到幾條thread幫忙做事
        30L,                    //多開的thread沒在使用時，活著的時間
        TimeUnit.SECONDS,                     //單位
        ArrayBlockingQueue(512),    //最大可容量512個等待的task
    )

    fun main(task: Runnable) {
        if (uiHandler == null) {
            uiHandler = Handler(Looper.getMainLooper())
        }
        uiHandler!!.post(task)
    }

    fun async(task: Runnable) {
        mMultiThreadExecutor.execute(task)
    }

    fun download(task: Runnable) {
        threadPoolExecutor.execute(task)
    }
}