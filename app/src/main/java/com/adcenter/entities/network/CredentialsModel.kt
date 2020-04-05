package com.adcenter.entities.network

import com.adcenter.extensions.Constants

data class CredentialsModel(
    val username: String = Constants.EMPTY,
    val password: String = Constants.EMPTY
)
