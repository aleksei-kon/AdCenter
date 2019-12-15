package com.adcenter.data.processors

import com.adcenter.data.getImageDownloadUrl
import com.adcenter.entities.network.NetworkAdItem
import com.adcenter.entities.view.AdItemModel
import com.adcenter.utils.Constants
import com.adcenter.utils.Constants.EMPTY
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class AdsDataProcessor(private val gson: Gson) : IDataProcessor<List<AdItemModel>> {

    override fun processResponse(response: String): List<AdItemModel> {
        val responseList = gson.fromJson<List<NetworkAdItem>>(
            response,
            object : TypeToken<List<NetworkAdItem>>() {}.type
        )

        return responseList.map { processNetworkModel(it) }
    }

    private fun processNetworkModel(networkModel: NetworkAdItem): AdItemModel {
        val id = networkModel.id
        val photoUrl = getImageDownloadUrl(networkModel.photoUrl ?: EMPTY)
        val title = networkModel.title ?: EMPTY
        val price = networkModel.price ?: EMPTY
        val place = networkModel.place ?: EMPTY
        val date = formatUnixtime(networkModel.date)
        val views = (networkModel.views ?: 0).toString()

        return AdItemModel(
            id = id,
            photoUrl = photoUrl,
            title = title,
            price = price,
            place = place,
            date = date,
            views = views
        )
    }

    private fun formatUnixtime(unixtime: Long?): String =
        if (unixtime != null) {
            val date = Date(unixtime * Constants.MILLISECONDS_PREFIX)
            val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT_PATTERN, Locale.getDefault())

            dateFormat.format(date)
        } else {
            EMPTY
        }
}