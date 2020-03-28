package com.adcenter.datasource.processors

import com.adcenter.entities.network.Message
import com.adcenter.entities.network.NetworkAppConfigModel
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.extensions.Constants.EMPTY
import com.google.gson.Gson
import java.util.*

class AppConfigProcessor(private val gson: Gson) : IDataProcessor<AppConfigInfo> {

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

    override fun processResponse(response: String): AppConfigInfo {
        isMessage(response)
        val responseModel =
            gson.fromJson<NetworkAppConfigModel>(response, NetworkAppConfigModel::class.java)

        return processNetworkModel(responseModel)
    }

    private fun processNetworkModel(networkModel: NetworkAppConfigModel): AppConfigInfo {
        val token = networkModel.token ?: EMPTY
        val isLoggedIn = networkModel.isloggedin ?: false
        val isAdmin = networkModel.isadmin ?: false

        return AppConfigInfo(
            token = token,
            isLoggedIn = isLoggedIn,
            isAdmin = isAdmin
        )
    }
}