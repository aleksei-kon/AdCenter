package com.adcenter.features.registration.usecase

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.registration.data.RegistrationRequestParams
import com.adcenter.features.registration.repository.IRegistrationRepository
import com.adcenter.utils.Result

class RegistrationUseCase(private val repository: IRegistrationRepository) : IRegistrationUseCase {

    override fun register(params: RegistrationRequestParams): Result<AppConfigInfo> =
        repository.register(params)
}