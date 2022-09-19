package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DtoItem (
    val resourceUrl: String = "",
    val textDescription: String = "",
) : Parcelable
