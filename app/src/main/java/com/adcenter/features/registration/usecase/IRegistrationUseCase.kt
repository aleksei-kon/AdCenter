package com.adcenter.features.registration.usecase

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.utils.Result

interface IRegistrationUseCase {

    suspend fun register(json: String): Result<AppConfigInfo>
}