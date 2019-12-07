package com.adcenter.ui.activities

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.adcenter.receivers.INetworkCallback
import com.adcenter.receivers.NetworkChangeReceiver

abstract class OfflineActivity : BaseActivity() {

    private val networkChangeReceiver: BroadcastReceiver =
        NetworkChangeReceiver(object : INetworkCallback {

            override fun onNetworkLost() {
                updateContentVisibility()
            }

            override fun onNetworkChanged(networkType: Int) {
                updateContentVisibility()
            }
        })

    protected abstract fun updateContentVisibility()

    override fun onResume() {
        super.onResume()

        registerReceiver(
            networkChangeReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onPause() {
        super.onPause()

        unregisterReceiver(networkChangeReceiver)
    }
}