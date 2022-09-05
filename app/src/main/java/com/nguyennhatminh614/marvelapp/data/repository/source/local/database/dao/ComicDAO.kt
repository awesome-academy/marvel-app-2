package com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao

import com.nguyennhatminh614.marvelapp.data.model.Comic

interface ComicDAO {
    fun checkExistComic(comic: Comic) : Boolean
    fun getAllFavoriteComic() : MutableList<Comic>
    fun addComicToFavoriteList(comic: Comic)
    fun removeComicFromFavoriteList(comic: Comic)
}
