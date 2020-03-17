package com.adcenter.features.login.usecase

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.login.data.LoginRequestParams
import com.adcenter.utils.Result

interface ILoginUseCase {

    fun login(params: LoginRequestParams): Result<AppConfigInfo>
}