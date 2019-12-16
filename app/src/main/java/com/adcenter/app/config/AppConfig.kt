package com.adcenter.app.config

import com.adcenter.app.config.backendurl.BackendUrlHolder
import com.adcenter.entities.view.AppConfigInfo
import org.koin.core.KoinComponent
import org.koin.core.inject

object AppConfig : KoinComponent {

    private val urlHolder: BackendUrlHolder by inject()
    private val appConfigManager: IAppConfigManager by inject()

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