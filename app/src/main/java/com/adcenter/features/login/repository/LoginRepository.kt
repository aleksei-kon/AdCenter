package com.adcenter.features.login.repository

import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.getLoginUrl
import com.adcenter.data.processors.AppConfigProcessor
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

class LoginRepository(private val processor: AppConfigProcessor) : ILoginRepository {

    override suspend fun login(json: String): Result<AppConfigInfo> {
        return withContext(Dispatchers.IO) {
            suspendCancellableCoroutine<Result<AppConfigInfo>> { continuation ->
                runCatching {
                    val request = NetworkDataRequest(
                        url = getLoginUrl(),
                        body = json
                    )

                    val response = Callable<AppConfigInfo>()
                        .setRequest(request)
                        .setProcessor(processor)
                        .call()

                    if (isActive) {
                        continuation.resume(Result.Success(response)) {}
                    } else {
                        continuation.cancel()
                    }
                }.onFailure {
                    continuation.resume(Result.Error(it)) {}
                }
            }
        }
    }
}