package com.adcenter.utils.token

interface ITokenManager {

    var token: String?

    val hasToken: Boolean

    fun removeToken()
}