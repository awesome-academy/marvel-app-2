package com.nguyennhatminh614.marvelapp

import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.repository.EventRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.screen.event.EventContract
import com.nguyennhatminh614.marvelapp.screen.event.EventPresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class EventPresenterUnitTest {
    private val view = mockk<EventContract.View>(relaxed = true)
    private val repository = mockk<EventRepository>()
    private val successData = mock<MutableList<Event>>()
    private val listener = slot<OnResultListener<MutableList<Event>>>()
    private val exception = mockk<Exception>()
    private val offset = 20
    private val presenter by lazy { EventPresenter(repository) }

    @Before
    fun setUp() {
        presenter.setView(view)
    }

    @Test
    fun `get event list remote success`() {
        every {
            repository.getEventListRemote(capture(listener))
        } answers {
            listener.captured.onSuccess(successData)
        }

        presenter.getListEventRemote()

        verify {
            view.onSuccess(successData)
        }
    }

    @Test
    fun `get event list remote failure`() {
        every {
            repository.getEventListRemote(capture(listener))
        } answers {
            listener.captured.onError(exception)
        }

        presenter.getListEventRemote()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `get event list remote with offset success`() {
        every {
            repository.getEventListRemoteWithOffset(offset, capture(listener))
        } answers {
            listener.captured.onSuccess(successData)
        }

        presenter.getListEventRemoteWithOffset(offset)

        verify {
            view.onSuccess(successData)
        }
    }

    @Test
    fun `get event list remote with offset failure`() {
        every {
            repository.getEventListRemoteWithOffset(offset, capture(listener))
        } answers {
            listener.captured.onError(exception)
        }

        presenter.getListEventRemoteWithOffset(offset)

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `check get instance presenter is unique`() {
        val presenter = EventPresenter.getInstance(repository)
        val presenter1 = EventPresenter.getInstance(repository)
        Assert.assertEquals(presenter, presenter1)
    }
}
