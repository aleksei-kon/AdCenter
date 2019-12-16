package com.adcenter.features.registration.usecase

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.registration.repository.IRegistrationRepository
import com.adcenter.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class RegistrationUseCase(private val repository: IRegistrationRepository) : IRegistrationUseCase {

    override suspend fun register(json: String): Result<AppConfigInfo> {
        return coroutineScope {
            val adsAsync = async { repository.register(json) }
            adsAsync.await()
        }
    }
}