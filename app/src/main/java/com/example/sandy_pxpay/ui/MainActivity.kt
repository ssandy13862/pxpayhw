package com.example.sandy_pxpay.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.sandy_pxpay.databinding.ActivityMainBinding
import com.example.sandy_pxpay.utils.VersionUtil
import com.example.sandy_pxpay.viewModel.MainViewModel
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding
    private lateinit var mainVM: MainViewModel
    private lateinit var bannerAdapter: BannerRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainVM = ViewModelProvider(this).get(MainViewModel::class.java)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        observerData()
        setupView()
    }

    private fun observerData() {
        mainVM.init()
        mainVM.qrCode.observe(this, {
            val intent = Intent(this, PayActivity::class.java)
            intent.putExtra(PayActivity.ARG_QRCODE, it)
            startActivity(intent)
        })

        mainVM.msgList.observe(this, {
            val intent = Intent(this, MessageActivity::class.java)
            intent.putParcelableArrayListExtra(MessageActivity.ARG_MSGLIST, it)
            startActivity(intent)
        })

        mainVM.bannerList.observe(this, {
            bannerAdapter.updateData(it)
            val layoutManager =
                SmoothLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            bind.rvMainBanner.layoutManager = layoutManager
            bind.rvMainBanner.setHasFixedSize(true)
            bind.rvMainBanner.adapter = bannerAdapter

            // 一次滑動一頁的定位效果
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(bind.rvMainBanner)

            // 自動換頁
            val scheduledExecutorService: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
            scheduledExecutorService.scheduleAtFixedRate({
                var nextPosition = layoutManager.findFirstVisibleItemPosition() + 1
                if (nextPosition == it.size) {
                    nextPosition = 0
                }
                bind.rvMainBanner.smoothScrollToPosition(nextPosition)
            }, 3000, 5000, TimeUnit.MILLISECONDS)

        })

        mainVM.errorCode.observe(this, {
            if (it) {
                AlertDialog.Builder(this)
                    .setMessage("系統忙碌中，請稍後再試")
                    .setPositiveButton("確定") { dialog, which -> dialog.dismiss() }
                    .create()
                    .show()
            }
        })
    }

    private fun setupView() {
        val version = VersionUtil.getOSAppVersion(this)

        bind.btnMainPay.setOnClickListener {
            mainVM.getQRCode(version)
        }
        bind.btnMainMessage.setOnClickListener {
            mainVM.getMessages(version)
        }
        bannerAdapter = BannerRecyclerViewAdapter(this)
        mainVM.getBanners(version)
    }


}