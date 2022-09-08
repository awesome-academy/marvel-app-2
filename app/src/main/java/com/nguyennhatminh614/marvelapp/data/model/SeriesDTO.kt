package com.nguyennhatminh614.marvelapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeriesDTO(
    override var resourceUrl: String = "",
    override var textDescription: String = "",
) : Parcelable, DtoItem(resourceUrl, textDescription)
