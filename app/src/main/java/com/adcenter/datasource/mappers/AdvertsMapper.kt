package com.adcenter.datasource.mappers

import com.adcenter.appconfig.IAppConfig
import com.adcenter.entities.database.AdItemDbEntity
import com.adcenter.entities.network.NetworkAdItem
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.Constants.DATE_FORMAT_PATTERN
import com.adcenter.extensions.Constants.EMPTY
import com.adcenter.extensions.Constants.MILLISECONDS_PREFIX
import java.text.SimpleDateFormat
import java.util.*

class AdvertsMapper(
    private val appConfig: IAppConfig
) {

    fun toAdItem(dbEntity: AdItemDbEntity): AdItemModel {
        val id = dbEntity.id
        val photoUrl = appConfig.imageUrl + dbEntity.photoUrl
        val title = dbEntity.title ?: EMPTY
        val price = dbEntity.price ?: EMPTY
        val place = dbEntity.place ?: EMPTY
        val date = formatUnixtime(dbEntity.date)
        val views = (dbEntity.views ?: 0).toString()

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

    fun toAdItem(networkModel: NetworkAdItem): AdItemModel {
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