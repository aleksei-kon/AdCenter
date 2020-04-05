package com.adcenter.features.registration.repository

import com.adcenter.entities.Result
import com.adcenter.datasource.mappers.AppConfigMapper
import com.adcenter.datasource.network.AccountService
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.registration.models.RegistrationRequestParams

class RegistrationRepository(
    private val accountService: AccountService,
    private val appConfigMapper: AppConfigMapper
) : IRegistrationRepository {

    override fun register(params: RegistrationRequestParams): Result<AppConfigInfo> =
        runCatching {
            val networkResponse = accountService
                .registerUser(params.credentialsModel)
                .execute()
                .body()

            val response = appConfigMapper.map(networkResponse)

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}