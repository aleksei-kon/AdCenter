package com.adcenter.features.login.repository

import com.adcenter.datasource.api.IApi
import com.adcenter.datasource.Callable
import com.adcenter.datasource.NetworkDataRequest
import com.adcenter.datasource.processors.AppConfigProcessor
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.login.models.LoginRequestParams
import com.adcenter.datasource.Result
import com.google.gson.Gson

class LoginRepository(
    private val processor: AppConfigProcessor,
    private val gson: Gson,
    private val api: IApi
) : ILoginRepository {

    override fun login(params: LoginRequestParams): Result<AppConfigInfo> =
        runCatching {
            val request = NetworkDataRequest(
                url = api.getLoginUrl(),
                body = gson.toJson(params)
            )

            val response = Callable<AppConfigInfo>()
                .setRequest(request)
                .setProcessor(processor)
                .call()

            Result.Success(response)
        }.getOrElse {
            Result.Error(it)
        }
}