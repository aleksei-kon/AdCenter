package com.adcenter.extensions

object Constants {

    const val APP_LOG_NAME = "com.adcenter.app_log"
    const val SPACE = ' '
    const val EMPTY = ""
    const val EMPTY_ID = -1
    const val DATE_FORMAT_PATTERN = "d MMM y H:mm"
    const val MILLISECONDS_PREFIX = 1000
    val LOGIN_AND_PASSWORD_LENGTH_RANGE = 4..16
    val TITLE_LENGTH_RANGE = 4..40
    const val LOCATION_MIN_LENGTH = 2
    const val SYNOPSIS_MIN_LENGTH = 4
    val CURRENCY_LIST = arrayOf("USD", "EUR", "BYR", "RUB", "PLN", "GBP")

    object Request {

        const val AUTHORIZATION_HEADER = "Authorization"
        const val MEDIA_TYPE_JSON = "application/json; charset=utf-8"
        const val MEDIA_TYPE_IMAGE = "image/jpg"
        const val FILE_FORM_PARAM = "uploadFile"
    }
}