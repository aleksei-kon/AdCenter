package com.adcenter.features.registration.repository

import com.adcenter.datasource.api.IApi
import com.adcenter.datasource.Callable
import com.adcenter.datasource.NetworkDataRequest
import com.adcenter.datasource.processors.AppConfigProcessor
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.registration.models.RegistrationRequestParams
import com.adcenter.datasource.Result
import com.google.gson.Gson

class RegistrationRepository(
    private val processor: AppConfigProcessor,
    private val gson: Gson,
    private val api: IApi
) : IRegistrationRepository {

    override fun register(params: RegistrationRequestParams): Result<AppConfigInfo> =
        runCatching {
            val request = NetworkDataRequest(
                url = api.getRegisterUrl(),
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