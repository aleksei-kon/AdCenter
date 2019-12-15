package com.adcenter.entities.view

import com.adcenter.utils.Constants.EMPTY

data class AppConfigInfo(
    val token: String = EMPTY,
    val isLoggedIn: Boolean = false,
    val isAdmin: Boolean = false
)