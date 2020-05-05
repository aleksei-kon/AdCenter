package com.adcenter.features.details.repository

import com.adcenter.entities.view.DetailsModel
import com.adcenter.features.details.models.DetailsRequestParams
import com.adcenter.entities.Result

interface IDetailsRepository {

    fun getDetails(params: DetailsRequestParams): Result<DetailsModel>

    fun showHide(advertId: Int): Result<Boolean>

    fun addDeleteBookmark(advertId: Int): Result<Boolean>

    fun delete(advertId: Int): Result<Boolean>
}