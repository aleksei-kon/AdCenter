package com.adcenter.utils.extensions

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager

val Context.connectivityManager: ConnectivityManager?
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

val Context.notificationManager: NotificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

val Context.telephonyManager: TelephonyManager?
    get() = getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager

val Context.wifiManager: WifiManager
    @SuppressLint("WifiManagerLeak")
    get() = getSystemService(Context.WIFI_SERVICE) as WifiManager

