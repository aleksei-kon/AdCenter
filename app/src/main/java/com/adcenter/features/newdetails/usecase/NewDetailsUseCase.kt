package com.adcenter.features.newdetails.usecase

import com.adcenter.features.newdetails.data.NewDetailsModel
import com.adcenter.features.newdetails.repository.INewDetailsRepository
import com.adcenter.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class NewDetailsUseCase(private val repository: INewDetailsRepository) : INewDetailsUseCase {

    override suspend fun upload(json: String): Result<NewDetailsModel> {
        return coroutineScope {
            val adsAsync = async { repository.addDetails(json) }
            adsAsync.await()
        }
    }
}