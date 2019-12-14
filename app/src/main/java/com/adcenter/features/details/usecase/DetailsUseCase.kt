package com.adcenter.features.details.usecase

import com.adcenter.entities.view.DetailsModel
import com.adcenter.features.details.data.DetailsRequestParams
import com.adcenter.features.details.repository.IDetailsRepository
import com.adcenter.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class DetailsUseCase(private val repository: IDetailsRepository) : IDetailsUseCase {

    override suspend fun load(requestParams: DetailsRequestParams): Result<DetailsModel> =
        coroutineScope {
            val detailsAsync = async { repository.getDetails(requestParams) }

            detailsAsync.await()
        }
}