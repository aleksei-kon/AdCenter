package com.adcenter.features.newdetails.repository

import com.adcenter.features.newdetails.data.NewDetailsModel
import com.adcenter.utils.Result

interface INewDetailsRepository {

    suspend fun addDetails(json: String): Result<NewDetailsModel>
}