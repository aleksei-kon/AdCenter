package com.adcenter.features.registration.usecase

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.registration.models.RegistrationRequestParams
import com.adcenter.features.registration.repository.IRegistrationRepository
import com.adcenter.datasource.Result

class RegistrationUseCase(private val repository: IRegistrationRepository) : IRegistrationUseCase {

    override fun register(params: RegistrationRequestParams): Result<AppConfigInfo> =
        repository.register(params)
}