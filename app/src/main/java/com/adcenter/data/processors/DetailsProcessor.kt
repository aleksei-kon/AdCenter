package com.adcenter.data.processors

import com.adcenter.api.IApi
import com.adcenter.entities.network.Message
import com.adcenter.entities.network.NetworkDetailsModel
import com.adcenter.entities.view.DetailsModel
import com.adcenter.resource.IResourceProvider
import com.adcenter.utils.Constants.DATE_FORMAT_PATTERN
import com.adcenter.utils.Constants.EMPTY
import com.adcenter.utils.Constants.MILLISECONDS_PREFIX
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class DetailsProcessor(
    private val resourceProvider: IResourceProvider,
    private val gson: Gson,
    private val api: IApi
) : IDataProcessor<DetailsModel> {

    private fun isMessage(response: String) {
        val message: Message = try {
            gson.fromJson<Message>(response, Message::class.java)
        } catch (e: Exception) {
            Message(null)
        }

        if (!message.message.isNullOrEmpty()) {
            throw MissingFormatArgumentException(message.message)
        }
    }

    override fun processResponse(response: String): DetailsModel {
        isMessage(response)
        val responseModel =
            gson.fromJson<NetworkDetailsModel>(response, NetworkDetailsModel::class.java)

        return processNetworkModel(responseModel)
    }

    private fun processNetworkModel(networkModel: NetworkDetailsModel): DetailsModel {
        val id = networkModel.id
        val photos = getPhotosUrls(networkModel.photos)
        val isBookmark = networkModel.isBookmark ?: false
        val isShown = networkModel.isShown ?: false
        val title = networkModel.title ?: EMPTY
        val price = networkModel.price ?: EMPTY
        val location = networkModel.location ?: EMPTY
        val date = getDate(formatUnixtime(networkModel.date))
        val views = getViews(networkModel.views)
        val synopsis = networkModel.synopsis ?: EMPTY
        val username = networkModel.username ?: EMPTY
        val phone = networkModel.phone ?: EMPTY

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
            photos.map { api.getImageDownloadUrl(it) }
        }

    private fun getViews(views: Int?): String =
        if (views != null) {
            "${resourceProvider.viewsPrefix}${views}"
        } else {
            EMPTY
        }

    private fun getDate(date: String): String =
        if (date.isEmpty()) {
            EMPTY
        } else {
            "${resourceProvider.datePrefix}$date"
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