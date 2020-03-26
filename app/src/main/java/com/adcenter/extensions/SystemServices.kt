package com.adcenter.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.View

val Context.layoutInflater: LayoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

val View.layoutInflater: LayoutInflater
    get() = this.context.layoutInflater

val Context.connectivityManager: ConnectivityManager?
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager