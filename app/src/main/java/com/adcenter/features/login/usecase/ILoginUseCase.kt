package com.adcenter.features.login.usecase

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.login.models.LoginRequestParams
import com.adcenter.datasource.Result

interface ILoginUseCase {

    fun login(params: LoginRequestParams): Result<AppConfigInfo>
}