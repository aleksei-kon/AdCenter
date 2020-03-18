package com.adcenter.config

import com.adcenter.entities.view.AppConfigInfo

class AppConfig(
    private val urlHolder: BackendUrlHolder,
    private val appConfigManager: IAppConfigManager
) {

    var backendUrl: String
        get() = urlHolder.url
        set(value) {
            urlHolder.url = value
        }

    var token: String? = null
        private set

    var isLoggedIn: Boolean = false
        private set

    var isAdmin: Boolean = false
        private set

    fun initConfig() {
        token = appConfigManager.token
        isLoggedIn = appConfigManager.isLoggedIn
        isAdmin = appConfigManager.isAdmin
    }

    fun updateConfig(appConfigInfo: AppConfigInfo) {
        if (appConfigInfo.token.isEmpty()) {
            appConfigManager.removeToken()
        } else {
            appConfigManager.token = appConfigInfo.token
        }

        appConfigManager.isLoggedIn = appConfigInfo.isLoggedIn
        appConfigManager.isAdmin = appConfigInfo.isAdmin

        initConfig()
    }
}