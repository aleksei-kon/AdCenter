package com.adcenter.features.login.repository

import com.adcenter.entities.Result
import com.adcenter.datasource.mappers.AppConfigMapper
import com.adcenter.datasource.network.AccountService
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.login.models.LoginRequestParams

class LoginRepository(
    private val accountService: AccountService,
    private val appConfigMapper: AppConfigMapper
) : ILoginRepository {

    override fun login(params: LoginRequestParams): Result<AppConfigInfo> =
        runCatching {
            val networkResponse = accountService
                .loginUser(params.credentialsModel)
                .execute()
                .body()

            val response = appConfigMapper.map(networkResponse)

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}