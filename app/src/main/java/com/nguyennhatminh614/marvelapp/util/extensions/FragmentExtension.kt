package com.nguyennhatminh614.marvelapp.util.extensions

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment

const val NOT_EXIST_PATH = "This path is not exist!!"

fun Fragment.navigateToDirectLink(urlPath: String) {
    if (urlPath.isNotEmpty()){
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlPath)))
    } else{
        Toast.makeText(context, NOT_EXIST_PATH,Toast.LENGTH_SHORT).show()
    }
}
