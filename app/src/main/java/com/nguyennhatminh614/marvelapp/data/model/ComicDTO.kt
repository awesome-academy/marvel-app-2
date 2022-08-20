package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ComicDTO(
    var resourceUri: String = "",
    var name: String = "",
    var firstCreatorName: String = "",
) : Parcelable
