package com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson

import com.nguyennhatminh614.marvelapp.data.model.CharacterEntry
import com.nguyennhatminh614.marvelapp.util.constant.APIConstant
import com.nguyennhatminh614.marvelapp.util.constant.Constant
import org.json.JSONObject

class ParseDataWithJson {
    fun parseJsonToData(jsonObject: JSONObject?, keyEntity: String): Any? {
        val data = mutableListOf<Any>()

        if (jsonObject?.getInt(APIConstant.REQUEST_CODE) != Constant.REQUEST_CODE_OK) {
            return null
        }

        val resultList = jsonObject.getJSONObject(CharacterEntry.GET_DATA)
            .getJSONArray(CharacterEntry.GET_RESPONSE_RESULT)
        when (keyEntity) {
            CharacterEntry.CHARACTERS -> {
                for (i in 0 until resultList.length()) {
                    data.add(parseJsonToObject(resultList.getJSONObject(i), keyEntity)!!)
                }
            }
        }

        return data
    }

    private fun parseJsonToObject(jsonObject: JSONObject?, keyEntity: String): Any? {
        if (jsonObject != null) {
            return when (keyEntity) {
                CharacterEntry.CHARACTERS -> ParseJson().characterParseJson(jsonObject)
                else -> null
            }
        }
        return null
    }
}
