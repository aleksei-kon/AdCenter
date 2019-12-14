package com.adcenter.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.view.LayoutInflater

val Context.layoutInflater: LayoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

val Context.connectivityManager: ConnectivityManager?
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

val Context.wifiManager: WifiManager
    @SuppressLint("WifiManagerLeak")
    get() = getSystemService(Context.WIFI_SERVICE) as WifiManager

