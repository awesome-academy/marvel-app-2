package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

class CreatorRepository(
    private val remote: ICreatorDataSource.Remote?
) : ICreatorDataSource.Remote {

    override fun getCreatorListRemote(listener: OnResultListener<MutableList<Creator>>) {
        remote?.getCreatorListRemote(listener)
    }

    companion object {
        private var instance: CreatorRepository? = null
        fun getInstance(remote: ICreatorDataSource.Remote) =
            synchronized(this) {
                instance ?: CreatorRepository(remote).also { instance = it }
            }
    }
}
