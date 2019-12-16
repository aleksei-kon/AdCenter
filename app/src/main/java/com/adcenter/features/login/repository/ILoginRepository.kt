package com.adcenter.features.login.repository

import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.utils.Result

interface ILoginRepository {

    suspend fun login(json: String): Result<AppConfigInfo>
}