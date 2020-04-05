package com.adcenter.features.login.usecase

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.login.models.LoginRequestParams
import com.adcenter.features.login.repository.ILoginRepository
import com.adcenter.entities.Result

class LoginUseCase(private val repository: ILoginRepository) : ILoginUseCase {

    override fun login(params: LoginRequestParams): Result<AppConfigInfo> = repository.login(params)
}