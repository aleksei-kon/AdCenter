package com.adcenter.appconfig

import android.content.Context
import android.content.SharedPreferences

private const val BACKEND_URL_PREFS = "BACKEND_URL_PREFS"
private const val URL = "BACKEND_URL"
private const val DEFAULT_URL = "0.0.0.0:8888"

class BackendUrlHolder(context: Context) : IBackendUrlHolder {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(BACKEND_URL_PREFS, Context.MODE_PRIVATE)

    override var url: String
        get() = preferences.getString(URL, DEFAULT_URL) ?: DEFAULT_URL
        set(value) = preferences.edit().putString(URL, value).apply()
}