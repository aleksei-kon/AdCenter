package com.adcenter.config

import com.adcenter.entities.view.AppConfigInfo

interface IAppConfig {

    var backendUrl: String

    val token: String?

    val isLoggedIn: Boolean

    val isAdmin: Boolean

    fun initConfig()

    fun updateConfig(appConfigInfo: AppConfigInfo)
}