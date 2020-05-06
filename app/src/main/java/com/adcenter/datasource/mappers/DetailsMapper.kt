package com.adcenter.datasource.mappers

import com.adcenter.appconfig.IAppConfig
import com.adcenter.entities.database.DetailsDbEntity
import com.adcenter.entities.network.NetworkDetailsModel
import com.adcenter.entities.view.DetailsModel
import com.adcenter.extensions.Constants.DATE_FORMAT_PATTERN
import com.adcenter.extensions.Constants.EMPTY
import com.adcenter.extensions.Constants.MILLISECONDS_PREFIX
import com.adcenter.resource.IResourceProvider
import java.text.SimpleDateFormat
import java.util.*

class DetailsMapper(
    private val appConfig: IAppConfig,
    private val resourceProvider: IResourceProvider
) {

    fun toDetailsModel(dbEntity: DetailsDbEntity): DetailsModel {
        val id = dbEntity.id
        val photos = getPhotosUrls(dbEntity.photos)
        val isBookmark = dbEntity.bookmark ?: false
        val isShown = dbEntity.showed ?: false
        val title = dbEntity.title ?: EMPTY
        val price = dbEntity.price ?: EMPTY
        val location = dbEntity.location ?: EMPTY
        val date = getDate(formatUnixtime(dbEntity.date))
        val views = getViews(dbEntity.views)
        val synopsis = dbEntity.synopsis ?: EMPTY
        val username = getUsername(dbEntity.username)
        val phone = getPhone(dbEntity.phone)

        return DetailsModel(
            id = id,
            photos = photos,
            isBookmark = isBookmark,
            isShown = isShown,
            title = title,
            price = price,
            location = location,
            date = date,
            views = views,
            synopsis = synopsis,
            username = username,
            phone = phone
        )
    }

    fun toDetailsModel(networkModel: NetworkDetailsModel): DetailsModel {
        val id = networkModel.id
        val photos = getPhotosUrls(networkModel.photos)
        val isBookmark = networkModel.bookmark ?: false
        val isShown = networkModel.showed ?: false
        val title = networkModel.title ?: EMPTY
        val price = networkModel.price ?: EMPTY
        val location = networkModel.location ?: EMPTY
        val date = getDate(formatUnixtime(networkModel.date))
        val views = getViews(networkModel.views)
        val synopsis = networkModel.synopsis ?: EMPTY
        val username = getUsername(networkModel.username)
        val phone = getPhone(networkModel.phone)

        return DetailsModel(
            id = id,
            photos = photos,
            isBookmark = isBookmark,
            isShown = isShown,
            title = title,
            price = price,
            location = location,
            date = date,
            views = views,
            synopsis = synopsis,
            username = username,
            phone = phone
        )
    }

    private fun getPhotosUrls(photos: List<String>?): List<String> =
        if (photos == null || photos.isEmpty()) {
            emptyList()
        } else {
            photos.map { appConfig.imageUrl + it }
        }

    private fun getViews(views: Int?): String =
        if (views != null) {
            "${resourceProvider.viewsPrefix} $views"
        } else {
            EMPTY
        }

    private fun getDate(date: String): String =
        if (date.isEmpty()) {
            EMPTY
        } else {
            "${resourceProvider.datePrefix} $date"
        }

    private fun getUsername(username: String?): String =
        if (username.isNullOrEmpty()) {
            EMPTY
        } else {
            "${resourceProvider.usernamePrefix} $username"
        }

    private fun getPhone(phone: String?): String =
        if (phone.isNullOrEmpty()) {
            EMPTY
        } else {
            "${resourceProvider.phonePrefix} $phone"
        }

    private fun formatUnixtime(unixtime: Long?): String =
        if (unixtime != null) {
            val date = Date(unixtime * MILLISECONDS_PREFIX)
            val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())

            dateFormat.format(date)
        } else {
            EMPTY
        }
}