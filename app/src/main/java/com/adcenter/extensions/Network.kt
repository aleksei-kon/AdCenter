package com.adcenter.extensions

import android.content.Context

const val NETWORK_TYPE_NONE = -1

fun Context?.getNetworkType(): Int =
    this?.connectivityManager?.activeNetworkInfo?.type ?: NETWORK_TYPE_NONE

fun Context?.isConnectedToNetwork(): Boolean =
    this?.connectivityManager?.activeNetworkInfo?.isConnected ?: false
