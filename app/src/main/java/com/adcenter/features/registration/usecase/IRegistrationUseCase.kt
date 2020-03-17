package com.adcenter.features.registration.usecase

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.registration.data.RegistrationRequestParams
import com.adcenter.utils.Result

interface IRegistrationUseCase {

    fun register(params: RegistrationRequestParams): Result<AppConfigInfo>
}