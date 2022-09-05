package com.nguyennhatminh614.marvelapp.screen.creator

import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.repository.CreatorRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.util.base.BasePresenter

class CreatorPresenter(
    private val creatorRepository: CreatorRepository
) : BasePresenter<CreatorContract.View>, CreatorContract.Presenter {
    private var view: CreatorContract.View? = null

    override fun onStart() {
        // Not support
    }

    override fun onStop() {
        // Not support
    }

    override fun setView(view: CreatorContract.View?) {
        this.view = view
    }

    override fun getCreatorListRemote() {
        creatorRepository.getCreatorListRemote(
            object : OnResultListener<MutableList<Creator>> {
                override fun onSuccess(data: MutableList<Creator>?) {
                    view?.onSuccess(data)
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                }
            }
        )
    }

    companion object {
        private var instance: CreatorPresenter? = null

        fun getInstance(creatorRepository: CreatorRepository) = synchronized(this) {
            instance ?: CreatorPresenter(creatorRepository).also { instance = it }
        }
    }
}
