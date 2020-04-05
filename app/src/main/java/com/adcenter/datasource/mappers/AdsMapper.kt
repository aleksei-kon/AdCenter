package com.adcenter.datasource.mappers

import com.adcenter.appconfig.IAppConfig
import com.adcenter.entities.network.NetworkAdItem
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.Constants.DATE_FORMAT_PATTERN
import com.adcenter.extensions.Constants.EMPTY
import com.adcenter.extensions.Constants.MILLISECONDS_PREFIX
import java.text.SimpleDateFormat
import java.util.*

class AdsMapper(
    private val appConfig: IAppConfig
) {

    fun map(items: List<NetworkAdItem>?): List<AdItemModel> =
        items?.map { processNetworkModel(it) } ?: throw IllegalArgumentException("Empty response")

    private fun processNetworkModel(networkModel: NetworkAdItem): AdItemModel {
        val id = networkModel.id
        val photoUrl = appConfig.imageUrl + networkModel.photoUrl
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
            val date = Date(unixtime * MILLISECONDS_PREFIX)
            val dateFormat =
                SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())

            dateFormat.format(date)
        } else {
            EMPTY
        }
}