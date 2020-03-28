package com.adcenter.datasource.processors

import com.adcenter.entities.network.Message
import com.google.gson.Gson
import java.util.*

class PhotoProcessor(private val gson: Gson) : IDataProcessor<String> {

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

    override fun processResponse(response: String): String {
        isMessage(response)

        return response
    }
}