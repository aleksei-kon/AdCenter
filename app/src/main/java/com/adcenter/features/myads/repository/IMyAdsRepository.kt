package com.adcenter.features.myads.repository

import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.myads.models.MyAdsRequestParams
import com.adcenter.entities.Result

interface IMyAdsRepository {

    fun getMyAds(params: MyAdsRequestParams): Result<List<AdItemModel>>
}
