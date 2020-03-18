package com.adcenter.features.login.repository

import com.adcenter.api.IApi
import com.adcenter.app.App
import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.processors.AppConfigProcessor
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.features.login.data.LoginRequestParams
import com.adcenter.utils.Result
import com.google.gson.Gson
import javax.inject.Inject

class LoginRepository(
    private val processor: AppConfigProcessor
) : ILoginRepository {

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var api: IApi

    init {
        App.appComponent.inject(this)
    }

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