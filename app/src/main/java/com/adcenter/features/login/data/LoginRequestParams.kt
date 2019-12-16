package com.adcenter.features.login.data

import com.adcenter.utils.Constants.EMPTY

data class LoginRequestParams(
    val username: String = EMPTY,
    val password: String = EMPTY
)