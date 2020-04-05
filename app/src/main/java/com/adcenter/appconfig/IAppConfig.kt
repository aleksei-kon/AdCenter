package com.adcenter.appconfig

import com.adcenter.entities.view.AppConfigInfo

interface IAppConfig {

    val backendUrl: String

    val imageUrl: String

    val token: String?

    val isLoggedIn: Boolean

    val isAdmin: Boolean

    fun initConfig()

    fun updateConfig(appConfigInfo: AppConfigInfo)
}