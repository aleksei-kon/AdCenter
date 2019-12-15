package com.adcenter.app.config.backendurl

import android.content.Context
import android.content.SharedPreferences
import com.adcenter.utils.Constants.EMPTY

class BackendUrlHolder(context: Context) {

    private val BACKEND_URL_PREFS = "BACKEND_URL_PREFS"
    private val URL = "BACKEND_URL"

    private val preferences: SharedPreferences =
        context.getSharedPreferences(BACKEND_URL_PREFS, Context.MODE_PRIVATE)

    var url: String
        get() = preferences.getString(URL, EMPTY) ?: EMPTY
        set(value) = preferences.edit().putString(URL, value).apply()
}