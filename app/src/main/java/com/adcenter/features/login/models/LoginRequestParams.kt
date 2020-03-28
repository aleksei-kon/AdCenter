package com.adcenter.features.login.models

import com.adcenter.extensions.Constants.EMPTY

data class LoginRequestParams(
    val username: String = EMPTY,
    val password: String = EMPTY
)