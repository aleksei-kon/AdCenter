package com.adcenter.data.processors

import com.adcenter.data.getImageDownloadUrl
import com.adcenter.entities.network.NetworkAdItem
import com.adcenter.entities.view.AdItemModel
import com.adcenter.utils.Constants.EMPTY
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.core.KoinComponent
import org.koin.core.inject

class AdsDataProcessor : IDataProcessor<List<AdItemModel>>, KoinComponent {

    private val gson: Gson by inject()

    override fun processResponse(response: String): List<AdItemModel> {
        val responseList = gson.fromJson<List<NetworkAdItem>>(
            response,
            object : TypeToken<List<NetworkAdItem>>() {}.type
        )

        return responseList.map { processNetworkModel(it) }
    }

    private fun processNetworkModel(networkModel: NetworkAdItem): AdItemModel {
        return AdItemModel(
            id = networkModel.id,
            photoUrl = getImageDownloadUrl(networkModel.photoUrl ?: EMPTY),
            title = networkModel.title ?: EMPTY,
            price = networkModel.price ?: EMPTY,
            place = networkModel.place ?: EMPTY,
            views = networkModel.views ?: 0
        )
    }
}