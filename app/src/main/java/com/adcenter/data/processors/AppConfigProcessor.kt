package com.adcenter.data.processors

import com.adcenter.entities.network.NetworkAppConfigModel
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.utils.Constants.EMPTY
import com.google.gson.Gson

class AppConfigProcessor(private val gson: Gson) : IDataProcessor<AppConfigInfo> {

    override fun processResponse(response: String): AppConfigInfo {
        val responseModel =
            gson.fromJson<NetworkAppConfigModel>(response, NetworkAppConfigModel::class.java)

        return processNetworkModel(responseModel)
    }

    private fun processNetworkModel(networkModel: NetworkAppConfigModel): AppConfigInfo {
        val token = networkModel.token ?: EMPTY
        val isLoggedIn = networkModel.isLoggedIn ?: false
        val isAdmin = networkModel.isAdmin ?: false

        return AppConfigInfo(
            token = token,
            isLoggedIn = isLoggedIn,
            isAdmin = isAdmin
        )
    }
}