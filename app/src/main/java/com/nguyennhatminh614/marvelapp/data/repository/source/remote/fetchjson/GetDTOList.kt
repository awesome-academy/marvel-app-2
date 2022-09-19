package com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson

import com.nguyennhatminh614.marvelapp.data.model.DtoItem
import com.nguyennhatminh614.marvelapp.util.constant.APIConstant
import org.json.JSONArray

fun getDetailObjectList(jsonArray: JSONArray) = ArrayList<DtoItem>().apply {
    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        this.add(
            DtoItem(
                resourceUrl = jsonObject.getString(APIConstant.GET_RESOURCE_URI),
                textDescription = jsonObject.getString(APIConstant.NAME)
            )
        )
    }
}
