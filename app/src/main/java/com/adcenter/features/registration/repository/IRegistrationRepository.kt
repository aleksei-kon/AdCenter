package com.adcenter.features.registration.repository

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.registration.data.RegistrationRequestParams
import com.adcenter.utils.Result

interface IRegistrationRepository {

    fun register(params: RegistrationRequestParams): Result<AppConfigInfo>
}