package com.adcenter.datasource.mappers

import com.adcenter.entities.network.NetworkAppConfigModel
import com.adcenter.entities.view.AppConfigInfo
import com.adcenter.extensions.Constants

class AppConfigMapper {

    fun map(networkModel: NetworkAppConfigModel?): AppConfigInfo =
        networkModel?.let { processNetworkModel(it) }
            ?: throw IllegalArgumentException("Empty response")

    private fun processNetworkModel(networkModel: NetworkAppConfigModel): AppConfigInfo {
        val token = networkModel.token ?: Constants.EMPTY
        val isLoggedIn = networkModel.isloggedin ?: false
        val isAdmin = networkModel.isadmin ?: false

        return AppConfigInfo(
            token = token,
            isLoggedIn = isLoggedIn,
            isAdmin = isAdmin
        )
    }
}