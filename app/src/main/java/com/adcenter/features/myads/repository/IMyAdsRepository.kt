package com.adcenter.features.myads.repository

import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.myads.data.MyAdsRequestParams
import com.adcenter.utils.Result

interface IMyAdsRepository {

    suspend fun getMyAds(params: MyAdsRequestParams): Result<List<AdItemModel>>
}
