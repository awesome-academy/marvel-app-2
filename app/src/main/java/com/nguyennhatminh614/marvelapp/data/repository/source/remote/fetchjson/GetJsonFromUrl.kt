package com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson

import android.os.Handler
import android.os.Looper
import com.nguyennhatminh614.marvelapp.util.constant.APIConstant
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.Executors

const val DEFAULT_NO_LIMIT = 0
const val DEFAULT_NO_OFFSET = 0

class GetJsonFromUrl<T>(
    private val urlString: String,
    private val keyEntity: String,
    private val listener: OnResultListener<T>,
) {

    private val mExecutor: Executor = Executors.newSingleThreadExecutor()
    private val mHandler = Handler(Looper.getMainLooper())
    private var exception: Exception? = null

    fun callAPI(
        nameQueryToken: String = "",
        titleQueryToken: String = "",
        limit: Int = DEFAULT_NO_LIMIT,
        isSingleObject: Boolean = false,
        offset: Int = DEFAULT_NO_OFFSET,
    ) {
        var apiCallString = APIConstant.BASE_URL + urlString + APIConstant.QUERY_TOKEN
        if (nameQueryToken.isNullOrEmpty().not()) {
            apiCallString += "&nameStartsWith=$nameQueryToken"
        }
        if (titleQueryToken.isNullOrEmpty().not()) {
            apiCallString += "&titleStartsWith=$titleQueryToken"
        }
        if (limit != DEFAULT_NO_LIMIT) {
            apiCallString += "&limit=$limit"
        }
        if (offset != DEFAULT_NO_OFFSET) {
            apiCallString += "&offset=$offset"
        }
        mExecutor.execute {
            val responseJson =
                getRequestJsonFromUrl(apiCallString)

            val responseResult = if (isSingleObject.not()) {
                ParseDataWithJson()
                    .parseJsonToData(JSONObject(responseJson), keyEntity) as? T
            } else {
                ParseDataWithJson().parseJsonToDataSingleObject(JSONObject(responseJson),
                    keyEntity) as? T
            }

            mHandler.post {
                if (responseResult != null) {
                    listener.onSuccess(responseResult)
                } else {
                    listener.onError(exception)
                }
            }
        }
    }

    private fun getRequestJsonFromUrl(urlString: String): String {
        val url = URL(urlString)
        val httpURLConnection = url.openConnection() as? HttpURLConnection
        httpURLConnection?.run {
            connectTimeout = TIME_OUT
            readTimeout = TIME_OUT
            requestMethod = METHOD_GET
            doOutput = true
            connect()
        }

        val bufferedReader = BufferedReader(InputStreamReader(url.openStream()))
        val stringBuilder = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
        bufferedReader.close()
        httpURLConnection?.disconnect()
        return stringBuilder.toString()
    }

    companion object {
        private const val TIME_OUT = 15000
        private const val METHOD_GET = "GET"
    }
}
