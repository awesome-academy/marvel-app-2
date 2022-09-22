package com.nguyennhatminh614.marvelapp

import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.repository.CreatorRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.screen.creator.CreatorContract
import com.nguyennhatminh614.marvelapp.screen.creator.CreatorPresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class CreatorPresenterUnitTest {
    private val view = mockk<CreatorContract.View>(relaxed = true)
    private val repository = mockk<CreatorRepository>()
    private val successData = mock<MutableList<Creator>>()
    private val listener = slot<OnResultListener<MutableList<Creator>>>()
    private val exception = mockk<Exception>()
    private val offset = 20
    private val presenter by lazy { CreatorPresenter(repository) }

    @Before
    fun setUp() {
        presenter.setView(view)
    }

    @Test
    fun `get creator list remote success`() {
        every {
            repository.getCreatorListRemote(capture(listener))
        } answers {
            listener.captured.onSuccess(successData)
        }

        presenter.getCreatorListRemote()

        verify {
            view.onSuccess(successData)
        }
    }

    @Test
    fun `get creator list remote failure`() {
        every {
            repository.getCreatorListRemote(capture(listener))
        } answers {
            listener.captured.onError(exception)
        }

        presenter.getCreatorListRemote()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `get creator list remote with offset success`() {
        every {
            repository.getCreatorListRemoteWithOffset(offset, capture(listener))
        } answers {
            listener.captured.onSuccess(successData)
        }

        presenter.getCreatorListRemoteWithOffset(offset)

        verify {
            view.onSuccessGetCreatorOffsetList(successData)
        }
    }

    @Test
    fun `get creator list remote with offset failure`() {
        every {
            repository.getCreatorListRemoteWithOffset(offset, capture(listener))
        } answers {
            listener.captured.onError(exception)
        }

        presenter.getCreatorListRemoteWithOffset(offset)

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `check get instance presenter is unique`() {
        val presenter = CreatorPresenter.getInstance(repository)
        val presenter1 = CreatorPresenter.getInstance(repository)
        Assert.assertEquals(presenter, presenter1)
    }
}
