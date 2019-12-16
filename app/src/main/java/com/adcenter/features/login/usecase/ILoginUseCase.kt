package com.adcenter.features.login.usecase

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.utils.Result

interface ILoginUseCase {

    suspend fun login(json: String): Result<AppConfigInfo>
}