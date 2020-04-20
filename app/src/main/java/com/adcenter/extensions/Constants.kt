package com.adcenter.extensions

object Constants {

    const val SPACE = ' '
    const val EMPTY = ""
    const val DATE_FORMAT_PATTERN = "d MMM y H:mm"
    const val MILLISECONDS_PREFIX = 1000
    val LOGIN_AND_PASSWORD_LENGTH_RANGE = 4..16

    object Request {

        const val AUTHORIZATION_HEADER = "Authorization"
        const val MEDIA_TYPE_JSON = "application/json; charset=utf-8"
        const val MEDIA_TYPE_IMAGE = "image/jpg"
        const val FILE_FORM_PARAM = "uploadFile"
    }
}