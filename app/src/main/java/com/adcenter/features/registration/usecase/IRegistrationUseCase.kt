package com.adcenter.features.registration.usecase

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.registration.models.RegistrationRequestParams
import com.adcenter.entities.Result

interface IRegistrationUseCase {

    fun register(params: RegistrationRequestParams): Result<AppConfigInfo>
}