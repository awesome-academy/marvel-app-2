package com.nguyennhatminh614.marvelapp.util.constant

import com.nguyennhatminh614.marvelapp.BuildConfig

object APIConstant {
    const val BASE_URL = "http://gateway.marvel.com"
    const val QUERY_TOKEN = "?hash=${BuildConfig.HASH}&ts=${BuildConfig.TIME_STAMP}" +
            "&apikey=${BuildConfig.PUBLIC_KEY}"
    const val GET_RESOURCE_URI = "resourceURI"
    const val NAME = "name"
    const val REQUEST_CODE = "code"
}
