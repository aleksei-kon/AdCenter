package com.adcenter.utils.token

import android.content.Context
import android.content.SharedPreferences
import com.adcenter.utils.token.TokenManagerConstants.ACCESS_TOKEN
import com.adcenter.utils.token.TokenManagerConstants.AUTH

class TokenManager(context: Context) : ITokenManager {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(AUTH, Context.MODE_PRIVATE)

    override fun getToken(): String? =
        preferences.getString(ACCESS_TOKEN, null)

    override fun hasToken(): Boolean =
        preferences.contains(ACCESS_TOKEN)

    override fun updateToken(token: String) =
        preferences.edit()
            .putString(ACCESS_TOKEN, token)
            .apply()

    override fun removeToken() =
        preferences.edit()
            .remove(ACCESS_TOKEN)
            .apply()
}