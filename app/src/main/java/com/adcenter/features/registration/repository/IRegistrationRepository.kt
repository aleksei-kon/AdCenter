package com.adcenter.features.registration.repository

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.utils.Result

interface IRegistrationRepository {

    suspend fun register(json: String): Result<AppConfigInfo>
}