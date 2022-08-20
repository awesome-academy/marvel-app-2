package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreatorDTO(
    var resourceUri: String = "",
    var name: String = "",
    var role: String = "",
) : Parcelable
