package com.adcenter.lastads.repository

import com.adcenter.lastads.data.AdModel
import com.adcenter.lastads.data.LastAdsRequestParams
import com.adcenter.utils.Result

interface ILastAdsRepository {

    suspend fun getLastAds(params: LastAdsRequestParams): Result<List<AdModel>>
}
