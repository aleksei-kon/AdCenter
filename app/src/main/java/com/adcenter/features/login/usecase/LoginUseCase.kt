package com.adcenter.features.login.usecase

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.login.repository.ILoginRepository
import com.adcenter.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class LoginUseCase(private val repository: ILoginRepository) : ILoginUseCase {

    override suspend fun login(json: String): Result<AppConfigInfo> {
        return coroutineScope {
            val adsAsync = async { repository.login(json) }
            adsAsync.await()
        }
    }
}