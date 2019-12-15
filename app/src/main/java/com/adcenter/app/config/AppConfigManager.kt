package com.adcenter.app.config

import android.content.Context
import android.content.SharedPreferences
import com.adcenter.app.config.TokenManagerConstants.ACCESS_TOKEN
import com.adcenter.app.config.TokenManagerConstants.APP_CONFIG
import com.adcenter.app.config.TokenManagerConstants.IS_ADMIN
import com.adcenter.app.config.TokenManagerConstants.IS_LOGGED_IN

class AppConfigManager(context: Context) : IAppConfigManager {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(APP_CONFIG, Context.MODE_PRIVATE)

    override var isLoggedIn: Boolean
        get() = preferences.getBoolean(IS_LOGGED_IN, false)
        set(value) = preferences.edit().putBoolean(IS_LOGGED_IN, value).apply()

    override var isAdmin: Boolean
        get() = preferences.getBoolean(IS_ADMIN, false)
        set(value) = preferences.edit().putBoolean(IS_ADMIN, value).apply()

    override var token: String?
        get() = preferences.getString(ACCESS_TOKEN, null)
        set(value) = preferences.edit().putString(ACCESS_TOKEN, value).apply()

    override val hasToken: Boolean
        get() = preferences.contains(ACCESS_TOKEN)

    override fun removeToken() =
        preferences.edit()
            .remove(ACCESS_TOKEN)
            .apply()
}