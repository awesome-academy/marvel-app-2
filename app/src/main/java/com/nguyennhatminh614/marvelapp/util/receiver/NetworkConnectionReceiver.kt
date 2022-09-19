package com.nguyennhatminh614.marvelapp.util.receiver

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import com.nguyennhatminh614.marvelapp.databinding.NoInternetDialogBinding
import com.nguyennhatminh614.marvelapp.util.NetworkUtils

class NetworkConnectionReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (NetworkUtils.isConnectedToInternet(p0).not()) {
            val binding = NoInternetDialogBinding.inflate(LayoutInflater.from(p0))
            val alertDialogBuilder = AlertDialog.Builder(p0).apply {
                setView(binding.root)
                setCancelable(false)
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.window?.setGravity(Gravity.CENTER)
            alertDialog.show()
            binding.buttonRetryConnect.setOnClickListener {
                alertDialog.dismiss()
                onReceive(p0, p1)
            }
        }
    }
}
