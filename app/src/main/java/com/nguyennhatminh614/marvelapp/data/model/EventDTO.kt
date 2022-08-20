package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventDTO(
    var resourceUri: String = "",
    var name: String = "",
) : Parcelable
