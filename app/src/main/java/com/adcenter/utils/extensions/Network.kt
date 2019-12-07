package com.adcenter.utils.extensions

import android.content.Context
import com.adcenter.utils.Constants.EMPTY

const val NETWORK_TYPE_NONE = -1

fun Context?.getNetworkCountryIso(): String = this?.telephonyManager?.networkCountryIso ?: EMPTY

fun Context?.getNetworkType(): Int =
    this?.connectivityManager?.activeNetworkInfo?.type ?: NETWORK_TYPE_NONE

fun Context?.isConnectedToNetwork(): Boolean =
    this?.connectivityManager?.activeNetworkInfo?.isConnected ?: false

fun Context.isNetworkTypeAvailable(networkType: Int): Boolean =
    connectivityManager?.getNetworkInfo(networkType)?.isAvailable ?: false
