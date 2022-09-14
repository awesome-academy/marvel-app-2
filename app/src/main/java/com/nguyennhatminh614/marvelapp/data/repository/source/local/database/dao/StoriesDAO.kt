package com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao

import com.nguyennhatminh614.marvelapp.data.model.Stories

interface StoriesDAO {
    fun checkExistStories(stories: Stories): Boolean
    fun getAllFavoriteStories(): MutableList<Stories>
    fun addStoriesToFavoriteList(stories: Stories)
    fun removeStoriesToFavoriteList(id: Int)
}
