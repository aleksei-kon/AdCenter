package com.adcenter.features.login.models

import com.adcenter.entities.network.CredentialsModel

data class LoginRequestParams(
    val credentialsModel: CredentialsModel = CredentialsModel()
)