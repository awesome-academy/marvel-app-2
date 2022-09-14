package com.nguyennhatminh614.marvelapp.screen.search

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.model.Series

interface SearchContract {
    interface View {
        fun onSuccessGetListCharacter(data: MutableList<Character>?)
        fun onSuccessGetListCreator(data: MutableList<Creator>?)
        fun onSuccessGetListComic(data: MutableList<Comic>?)
        fun onSuccessGetListEvent(data: MutableList<Event>?)
        fun onSuccessGetListSeries(data: MutableList<Series>?)
        fun onError(exception: Exception?)
    }

    interface Presenter {
        fun queryComicName(stringRequest: String)
        fun queryCharacterName(stringRequest: String)
        fun queryEventName(stringRequest: String)
        fun querySeriesName(stringRequest: String)
        fun queryCreatorName(stringRequest: String)
    }
}
