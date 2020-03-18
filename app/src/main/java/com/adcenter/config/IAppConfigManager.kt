package com.adcenter.config

interface IAppConfigManager {

    var isLoggedIn: Boolean

    var isAdmin: Boolean

    var token: String?

    val hasToken: Boolean

    fun removeToken()
}