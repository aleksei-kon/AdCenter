package com.adcenter.appconfig

interface IAppConfigManager {

    var isLoggedIn: Boolean

    var isAdmin: Boolean

    var token: String?

    val hasToken: Boolean

    fun removeToken()
}