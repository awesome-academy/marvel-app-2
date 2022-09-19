package com.nguyennhatminh614.marvelapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkUtils {
    fun isConnectedToInternet(context: Context?): Boolean {
        val connectManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities =
                connectManager.getNetworkCapabilities(connectManager.activeNetwork)
            if (networkCapabilities != null) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    true
                } else networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            } else {
                false
            }
        } else {
            val networkInfo = connectManager.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        }
    }
}
