package com.adcenter.utils

object Constants {

    const val EMPTY = ""
    const val DATE_FORMAT_PATTERN = "d MMM y H:mm"
    const val REQUEST_DELAY = 1200L
    const val MILLISECONDS_PREFIX = 1000
    const val IMAGE_ROUNDED_CORNERS = 16

    object Request {

        const val AUTHORIZATION_HEADER = "Authorization"
        const val MEDIA_TYPE_JSON = "application/json; charset=utf-8"
        const val MEDIA_TYPE_IMAGE = "image/jpg"
        const val FILE_FORM_PARAM = "uploadFile"
    }
}