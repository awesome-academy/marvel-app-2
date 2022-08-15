package com.nguyennhatminh614.marvelapp.util.constant

import com.nguyennhatminh614.marvelapp.BuildConfig

object Constant {
    const val BASE_URL = "http://gateway.marvel.com"
    const val SHARED_PREFERENCE_FILE = "com.nguyennhatminh614.marvelapp"
    const val QUERY_TOKEN = "?hash=${BuildConfig.HASH}&timestamp=${BuildConfig.TIME_STAMP}&apikey=${BuildConfig.PUBLIC_KEY}"
}
