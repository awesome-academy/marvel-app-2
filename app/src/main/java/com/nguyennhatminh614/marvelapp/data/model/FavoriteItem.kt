package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteItem (
    val id: Int = 0,
    val thumbnailLink: String = "",
    val title: String = "",
    val favoriteItemType: String = "",
) : Parcelable
