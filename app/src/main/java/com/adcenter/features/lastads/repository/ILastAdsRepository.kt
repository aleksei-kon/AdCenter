package com.adcenter.features.lastads.repository

import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.lastads.data.LastAdsRequestParams
import com.adcenter.utils.Result

interface ILastAdsRepository {

    suspend fun getLastAds(params: LastAdsRequestParams): Result<List<AdItemModel>>
}
