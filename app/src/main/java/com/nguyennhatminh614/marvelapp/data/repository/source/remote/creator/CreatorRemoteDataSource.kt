package com.nguyennhatminh614.marvelapp.data.repository.source.remote.creator

import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.model.CreatorEntry
import com.nguyennhatminh614.marvelapp.data.repository.ICreatorDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.GetJsonFromUrl
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class CreatorRemoteDataSource : ICreatorDataSource.Remote {
    override fun getCreatorListRemote(listener: OnResultListener<MutableList<Creator>>) {
        GetJsonFromUrl(GET_ALL_CREATOR, CreatorEntry.CREATOR_ENTITY, listener)
    }

    companion object {
        const val GET_ALL_CREATOR = "/v1/public/creators"
        private var instance: CreatorRemoteDataSource? = null
        fun getInstance() =
            synchronized(this) {
                instance ?: CreatorRemoteDataSource().also { instance = it }
            }
    }
}
