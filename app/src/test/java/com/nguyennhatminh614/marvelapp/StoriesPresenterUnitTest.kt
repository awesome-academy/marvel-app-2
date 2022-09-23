package com.nguyennhatminh614.marvelapp

import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.repository.StoriesRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.screen.stories.StoriesContract
import com.nguyennhatminh614.marvelapp.screen.stories.StoriesPresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class StoriesPresenterUnitTest {
    private val view = mockk<StoriesContract.View>(relaxed = true)
    private val repository = mockk<StoriesRepository>()
    private val successData = mock<MutableList<Stories>>()
    private val listener = slot<OnResultListener<MutableList<Stories>>>()
    private val exception = mockk<Exception>()
    private val offset = 20
    private val stories = mockk<Stories>()
    private val id = 2020222
    private val presenter by lazy { StoriesPresenter(repository) }

    @Before
    fun setUp() {
        presenter.setView(view)
    }

    @Test
    fun `get stories list from local success`() {
        every {
            repository.getAllFavoriteListLocal(capture(listener))
        } answers {
            listener.captured.onSuccess(successData)
        }

        presenter.getStoriesListFromLocal()

        verify {
            view.onSuccessGetFavoriteItem(successData)
        }
    }

    @Test
    fun `get stories list from local failure`() {
        every {
            repository.getAllFavoriteListLocal(capture(listener))
        } answers {
            listener.captured.onError(exception)
        }

        presenter.getStoriesListFromLocal()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun  `get stories list remote success`() {
        every {
            repository.getRemoteListStories(capture(listener))
        } answers {
            listener.captured.onSuccess(successData)
        }

        presenter.getStoriesListRemote()

        verify {
            view.onSuccessGetDataFromRemote(successData)
        }
    }

    @Test
    fun `get stories list remote failure`() {
        every {
            repository.getRemoteListStories(capture(listener))
        } answers {
            listener.captured.onError(exception)
        }

        presenter.getStoriesListRemote()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `get stories list remote with offset success`() {
        every {
            repository.getRemoteListStoriesWithOffset(offset, capture(listener))
        } answers {
            listener.captured.onSuccess(successData)
        }

        presenter.getStoriesListRemoteWithOffset(offset)

        verify {
            view.onSuccessGetOffsetDataFromRemote(successData)
        }
    }

    @Test
    fun `get stories list remote with offset failure`() {
        every {
            repository.getRemoteListStoriesWithOffset(offset, capture(listener))
        } answers {
            listener.captured.onError(exception)
        }

        presenter.getStoriesListRemoteWithOffset(offset)

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `check stories is exists in favorite list`() {
        every {
            repository.checkExistStories(stories)
        } returns true

        Assert.assertEquals(presenter.checkFavoriteItemExist(stories), true)
    }

    @Test
    fun `check stories is not exists in favorite list`() {
        every {
            repository.checkExistStories(stories)
        } answers {
            false
        }

        Assert.assertEquals(presenter.checkFavoriteItemExist(stories), false)
    }

    @Test
    fun `add stories success`() {
        every {
            repository.addStoriesToFavoriteList(stories)
        } answers {
            true
        }

        Assert.assertEquals(presenter.addStoriesFavoriteToListLocal(stories), true)
    }

    @Test
    fun `add stories fail`() {
        every {
            repository.addStoriesToFavoriteList(stories)
        } answers {
            false
        }

        Assert.assertEquals(presenter.addStoriesFavoriteToListLocal(stories), false)
    }

    @Test
    fun `remove stories success`() {
        every {
            repository.removeStoriesFromFavoriteList(id)
        } answers {
            true
        }

        Assert.assertEquals(presenter.removeStoriesFavoriteToListLocal(id), true)
    }

    @Test
    fun `remove stories failure`() {
        val id = 2020222
        every {
            repository.removeStoriesFromFavoriteList(id)
        } answers {
            false
        }

        Assert.assertEquals(presenter.removeStoriesFavoriteToListLocal(id), false)
    }


    @Test
    fun `get instance test`() {
        val presenter = StoriesPresenter.getInstance(repository)
        val secondPresenter = StoriesPresenter.getInstance(repository)
        Assert.assertEquals(presenter, secondPresenter)
    }
}
