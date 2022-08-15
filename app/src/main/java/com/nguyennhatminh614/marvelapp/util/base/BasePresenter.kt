package com.nguyennhatminh614.marvelapp.util.base

interface BasePresenter<T> {
    fun onStart()
    fun onStop()
    fun setView(view: T?)
}
