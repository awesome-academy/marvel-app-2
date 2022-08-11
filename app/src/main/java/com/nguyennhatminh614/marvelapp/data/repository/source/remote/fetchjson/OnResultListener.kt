package com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson

interface OnResultListener<T> {
    fun onSuccess(data: T)
    fun onError(exception: Exception?)
}
