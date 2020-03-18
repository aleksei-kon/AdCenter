package com.adcenter.config

import android.content.Context
import android.content.SharedPreferences

private const val APP_CONFIG = "APP_CONFIG"
private const val ACCESS_TOKEN = "ACCESS_TOKEN"
private const val IS_LOGGED_IN = "IS_LOGGED_IN"
private const val IS_ADMIN = "IS_ADMIN"

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