package com.adcenter.myads.repository

import com.adcenter.entities.AdModel
import com.adcenter.myads.data.MyAdsRequestParams
import com.adcenter.utils.Result

interface IMyAdsRepository {

    suspend fun getMyAds(params: MyAdsRequestParams): Result<List<AdModel>>
}
