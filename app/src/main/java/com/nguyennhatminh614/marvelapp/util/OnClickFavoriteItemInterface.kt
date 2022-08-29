package com.nguyennhatminh614.marvelapp.util

interface OnClickFavoriteItemInterface<T> {
    fun onFavoriteItem(item: T)
    fun onUnfavoriteItem(item: T)
}
