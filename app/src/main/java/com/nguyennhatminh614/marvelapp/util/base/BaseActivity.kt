package com.nguyennhatminh614.marvelapp.util.base

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nguyennhatminh614.marvelapp.util.receiver.NetworkConnectionReceiver

abstract class BaseActivity : AppCompatActivity() {
    private val networkConnectionReceiver = NetworkConnectionReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkConnectionReceiver, intentFilter)
        initView()
        initData()
        initEvent()
    }

    override fun onDestroy() {
        unregisterReceiver(networkConnectionReceiver)
        super.onDestroy()
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun initEvent()
}
