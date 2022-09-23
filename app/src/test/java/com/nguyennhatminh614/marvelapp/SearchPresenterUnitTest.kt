package com.nguyennhatminh614.marvelapp

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.repository.SearchRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.screen.search.SearchContract
import com.nguyennhatminh614.marvelapp.screen.search.SearchPresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class SearchPresenterUnitTest {
    private val view = mockk<SearchContract.View>(relaxed = true)
    private val repository = mockk<SearchRepository>()
    private val successComicData = mock<MutableList<Comic>>()
    private val successCharacterData = mock<MutableList<Character>>()
    private val successEventData = mock<MutableList<Event>>()
    private val successSeriesData = mock<MutableList<Series>>()
    private val successCreatorData = mock<MutableList<Creator>>()
    private val comicListener = slot<OnResultListener<MutableList<Comic>>>()
    private val characterListener = slot<OnResultListener<MutableList<Character>>>()
    private val eventListener = slot<OnResultListener<MutableList<Event>>>()
    private val seriesListener = slot<OnResultListener<MutableList<Series>>>()
    private val creatorListener = slot<OnResultListener<MutableList<Creator>>>()
    private val exception = mockk<Exception>()
    private val stringRequest = "test search"
    private val presenter by lazy { SearchPresenter(repository) }

    @Before
    fun setUp() {
        presenter.setView(view)
    }

    @Test
    fun `query comic name success`() {
        every {
            repository.getRemoteListComicByName(stringRequest, capture(comicListener))
        } answers {
            comicListener.captured.onSuccess(successComicData)
        }

        presenter.queryComicName(stringRequest)

        verify {
            view.onSuccessGetListComic(successComicData)
        }
    }

    @Test
    fun `query comic name fail`() {
        every {
            repository.getRemoteListComicByName(stringRequest, capture(comicListener))
        } answers {
            comicListener.captured.onError(exception)
        }

        presenter.queryComicName(stringRequest)

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `query character name success`() {
        every {
            repository.getRemoteListCharacterByName(stringRequest, capture(characterListener))
        } answers {
            characterListener.captured.onSuccess(successCharacterData)
        }

        presenter.queryCharacterName(stringRequest)

        verify {
            view.onSuccessGetListCharacter(successCharacterData)
        }
    }

    @Test
    fun `query character name fail`() {
        every {
            repository.getRemoteListCharacterByName(stringRequest, capture(characterListener))
        } answers {
            characterListener.captured.onError(exception)
        }

        presenter.queryCharacterName(stringRequest)

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `query event name success`() {
        every {
            repository.getRemoteListEventByName(stringRequest, capture(eventListener))
        } answers {
            eventListener.captured.onSuccess(successEventData)
        }

        presenter.queryEventName(stringRequest)

        verify {
            view.onSuccessGetListEvent(successEventData)
        }
    }

    @Test
    fun `query event name fail`() {
        every {
            repository.getRemoteListEventByName(stringRequest, capture(eventListener))
        } answers {
            eventListener.captured.onError(exception)
        }

        presenter.queryEventName(stringRequest)

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `query series name success`() {
        every {
            repository.getRemoteListSeriesByName(stringRequest, capture(seriesListener))
        } answers {
            seriesListener.captured.onSuccess(successSeriesData)
        }

        presenter.querySeriesName(stringRequest)

        verify {
            view.onSuccessGetListSeries(successSeriesData)
        }
    }

    @Test
    fun `query series name fail`() {
        every {
            repository.getRemoteListSeriesByName(stringRequest, capture(seriesListener))
        } answers {
            seriesListener.captured.onError(exception)
        }

        presenter.querySeriesName(stringRequest)

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `query creator name success`() {
        every {
            repository.getRemoteListCreatorByName(stringRequest, capture(creatorListener))
        } answers {
            creatorListener.captured.onSuccess(successCreatorData)
        }

        presenter.queryCreatorName(stringRequest)

        verify {
            view.onSuccessGetListCreator(successCreatorData)
        }
    }

    @Test
    fun `query creator name fail`() {
        every {
            repository.getRemoteListCreatorByName(stringRequest, capture(creatorListener))
        } answers {
            creatorListener.captured.onError(exception)
        }

        presenter.queryCreatorName(stringRequest)

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `check get instance is unique`(){
        val presenter = SearchPresenter.getInstance(repository)
        val presenter1 = SearchPresenter.getInstance(repository)
        Assert.assertEquals(presenter, presenter1)
    }
}
