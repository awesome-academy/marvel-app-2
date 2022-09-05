package com.nguyennhatminh614.marvelapp.data.repository

import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener

interface ICreatorDataSource {
    interface Remote {
        fun getCreatorListRemote(listener: OnResultListener<MutableList<Creator>>)
    }
}
