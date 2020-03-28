package com.adcenter.features.lastads.repository

import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.lastads.models.LastAdsRequestParams
import com.adcenter.datasource.Result

interface ILastAdsRepository {

    fun getLastAds(params: LastAdsRequestParams): Result<List<AdItemModel>>
}
