package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailListItem(
    val content: String,
    val type: DetailType,
) : Parcelable

enum class DetailType {
    TITLE,
    CONTENT
}