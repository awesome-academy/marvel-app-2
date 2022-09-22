package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteItemList(
    val title: String? = null,
    val favoriteItem: FavoriteItem? = null,
    val type: String,
) : Parcelable

enum class FavoriteItemListType(val type: String) {
    TITLE("title"),
    CONTENT("content")
}
