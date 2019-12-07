package com.adcenter.receivers

interface INetworkCallback {

    fun onNetworkLost()

    fun onNetworkChanged(networkType: Int)
}
