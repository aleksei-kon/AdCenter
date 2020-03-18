package com.adcenter.config

import com.adcenter.entities.view.AppConfigInfo

class AppConfig(
    private val urlHolder: IBackendUrlHolder,
    private val appConfigManager: IAppConfigManager
) : IAppConfig {

    override var backendUrl: String
        get() = urlHolder.url
        set(value) {
            urlHolder.url = value
        }

    override var token: String? = null
        private set

    override var isLoggedIn: Boolean = false
        private set

    override var isAdmin: Boolean = false
        private set

    override fun initConfig() {
        token = appConfigManager.token
        isLoggedIn = appConfigManager.isLoggedIn
        isAdmin = appConfigManager.isAdmin
    }

    override fun updateConfig(appConfigInfo: AppConfigInfo) {
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