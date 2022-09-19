package com.nguyennhatminh614.marvelapp.util.extensions

const val UNDEFINED = "Undefined!!"
const val DATE_LENGTH = 10
const val HTTP = "http"
const val HTTPS = "https"

fun String?.parseDateTime(): String {
    if (this?.length!! >= DATE_LENGTH) {
        return this?.substring(0, DATE_LENGTH)?.trim()!!
    }
    return UNDEFINED
}

fun String.processSearchResult(): String {
    return this.split(" ")
        .filter { it.isNullOrEmpty().not() }.joinToString(separator = "_")
}

fun String.convertToHTTPS() : String {
    return this.replace(HTTP, HTTPS)
}