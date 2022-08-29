package com.nguyennhatminh614.marvelapp.util.extensions

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadGlideImageFromUrl(context: Context, urlString: String, defaultImageID: Int) {
    Glide.with(context)
        .load(urlString)
        .error(defaultImageID)
        .into(this)
}
