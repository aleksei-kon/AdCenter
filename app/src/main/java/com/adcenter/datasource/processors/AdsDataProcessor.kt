package com.adcenter.datasource.processors

import com.adcenter.datasource.api.IApi
import com.adcenter.entities.network.Message
import com.adcenter.entities.network.NetworkAdItem
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.Constants
import com.adcenter.extensions.Constants.EMPTY
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class AdsDataProcessor(
    private val gson: Gson,
    private val api: IApi
) : IDataProcessor<List<AdItemModel>> {

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

    override fun processResponse(response: String): List<AdItemModel> {
        isMessage(response)

        val responseList = gson.fromJson<List<NetworkAdItem>>(
            response,
            object : TypeToken<List<NetworkAdItem>>() {}.type
        )

        return responseList.map { processNetworkModel(it) }
    }

    private fun processNetworkModel(networkModel: NetworkAdItem): AdItemModel {
        val id = networkModel.id
        val photoUrl = api.getImageDownloadUrl(networkModel.photoUrl ?: EMPTY)
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
            val dateFormat =
                SimpleDateFormat(Constants.DATE_FORMAT_PATTERN, Locale.getDefault())

            dateFormat.format(date)
        } else {
            EMPTY
        }
}