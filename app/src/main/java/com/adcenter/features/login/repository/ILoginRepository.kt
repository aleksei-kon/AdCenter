package com.adcenter.features.login.repository

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.login.data.LoginRequestParams
import com.adcenter.utils.Result

interface ILoginRepository {

    fun login(params: LoginRequestParams): Result<AppConfigInfo>
}