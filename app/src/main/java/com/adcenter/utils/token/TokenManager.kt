package com.adcenter.utils.token

import android.content.Context
import android.content.SharedPreferences
import com.adcenter.utils.token.TokenManagerConstants.ACCESS_TOKEN
import com.adcenter.utils.token.TokenManagerConstants.AUTH

class TokenManager(context: Context) : ITokenManager {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(AUTH, Context.MODE_PRIVATE)

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