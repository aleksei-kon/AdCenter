package com.adcenter.features.login.repository

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.login.models.LoginRequestParams
import com.adcenter.entities.Result

interface ILoginRepository {

    fun login(params: LoginRequestParams): Result<AppConfigInfo>
}