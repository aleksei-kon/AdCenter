package com.adcenter.features.registration.repository

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.registration.models.RegistrationRequestParams
import com.adcenter.entities.Result

interface IRegistrationRepository {

    fun register(params: RegistrationRequestParams): Result<AppConfigInfo>
}