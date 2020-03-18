package com.adcenter.data.processors

import com.adcenter.app.App
import com.adcenter.entities.network.Message
import com.google.gson.Gson
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class ShowHideProcessor : IDataProcessor<Boolean> {

    @Inject
    lateinit var gson: Gson

    init {
        App.appComponent.inject(this)
    }

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

    override fun processResponse(response: String): Boolean {
        isMessage(response)

        return response.toBoolean()
    }
}