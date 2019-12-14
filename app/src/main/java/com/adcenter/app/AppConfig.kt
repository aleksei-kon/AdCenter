package com.adcenter.app

import com.adcenter.utils.Constants.EMPTY
import com.adcenter.utils.backendurl.BackendUrlHolder
import org.koin.core.KoinComponent
import org.koin.core.inject

object AppConfig : KoinComponent {

    private val urlHolder: BackendUrlHolder by inject()

    var backendUrl: String = EMPTY

    var isGuest: Boolean = true

    var isAdmin: Boolean = false

    fun initConfig() {
        backendUrl = urlHolder.url
    }
}