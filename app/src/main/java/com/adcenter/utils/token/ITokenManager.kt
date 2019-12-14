package com.adcenter.utils.token

interface ITokenManager {

    fun getToken(): String?

    fun hasToken(): Boolean

    fun updateToken(token: String)

    fun removeToken()
}