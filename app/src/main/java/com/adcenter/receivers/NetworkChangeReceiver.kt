package com.adcenter.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.adcenter.utils.extensions.getNetworkType
import com.adcenter.utils.extensions.isConnectedToNetwork

internal class NetworkChangeReceiver(private val networkCallback: INetworkCallback) :
    BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent?.action) {
            context?.let {
                if (it.isConnectedToNetwork()) {
                    networkCallback.onNetworkChanged(it.getNetworkType())
                } else {
                    networkCallback.onNetworkLost()
                }
            }
        }
    }
}