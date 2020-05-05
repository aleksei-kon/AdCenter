package com.adcenter.appconfig

import android.content.Context
import android.content.Intent
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.receivers.LOGIN_STATE_CHANGED_ACTION

class AppConfig(
    private val urlHolder: IBackendUrlHolder,
    private val appConfigManager: IAppConfigManager,
    private val context: Context
) : IAppConfig {

    override val backendUrl: String
        get() = "http://${urlHolder.url}/"

    override val imageUrl: String
        get() = "${backendUrl}image/download/"

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
        context.sendBroadcast(Intent().setAction(LOGIN_STATE_CHANGED_ACTION))
    }
}