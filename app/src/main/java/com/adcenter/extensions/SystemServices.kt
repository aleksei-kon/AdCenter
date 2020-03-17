package com.adcenter.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater

val Context.layoutInflater: LayoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

val Context.connectivityManager: ConnectivityManager?
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager