package com.nguyennhatminh614.marvelapp

import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.repository.SeriesRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.screen.series.SeriesContract
import com.nguyennhatminh614.marvelapp.screen.series.SeriesPresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class SeriesPresenterUnitTest {
    private val view = mockk<SeriesContract.View>(relaxed = true)
    private val repository = mockk<SeriesRepository>()
    private val successData = mock<MutableList<Series>>()
    private val listener = slot<OnResultListener<MutableList<Series>>>()
    private val exception = mockk<Exception>()
    private val offset = 20
    private val series = mockk<Series>()
    private val id = 2020222
    private val presenter by lazy { SeriesPresenter(repository) }

    @Before
    fun setUp() {
        presenter.setView(view)
    }

    @Test
    fun `get series list from local success`() {
        every {
            repository.getAllFavoriteListLocal(capture(listener))
        } answers {
            listener.captured.onSuccess(successData)
        }

        presenter.getSeriesListFromLocal()

        verify {
            view.onSuccessGetFavoriteItem(successData)
        }
    }

    @Test
    fun `get series list from local failure`() {
        every {
            repository.getAllFavoriteListLocal(capture(listener))
        } answers {
            listener.captured.onError(exception)
        }

        presenter.getSeriesListFromLocal()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun  `get series list remote success`() {
        every {
            repository.getRemoteListSeries(capture(listener))
        } answers {
            listener.captured.onSuccess(successData)
        }

        presenter.getSeriesListRemote()

        verify {
            view.onSuccessGetDataFromRemote(successData)
        }
    }

    @Test
    fun `get series list remote failure`() {
        every {
            repository.getRemoteListSeries(capture(listener))
        } answers {
            listener.captured.onError(exception)
        }

        presenter.getSeriesListRemote()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `get series list remote with offset success`() {
        every {
            repository.getRemoteListSeriesWithOffset(offset, capture(listener))
        } answers {
            listener.captured.onSuccess(successData)
        }

        presenter.getSeriesListRemoteWithOffset(offset)

        verify {
            view.onSuccessGetOffsetDataFromRemote(successData)
        }
    }

    @Test
    fun `get series list remote with offset failure`() {
        every {
            repository.getRemoteListSeriesWithOffset(offset, capture(listener))
        } answers {
            listener.captured.onError(exception)
        }

        presenter.getSeriesListRemoteWithOffset(offset)

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `check series is exists in favorite list`() {
        every {
            repository.checkExistSeries(series)
        } returns true

        Assert.assertEquals(presenter.checkFavoriteItemExist(series), true)
    }

    @Test
    fun `check series is not exists in favorite list`() {
        every {
            repository.checkExistSeries(series)
        } answers {
            false
        }

        Assert.assertEquals(presenter.checkFavoriteItemExist(series), false)
    }

    @Test
    fun `add series success`() {
        every {
            repository.addSeriesToFavoriteList(series)
        } answers {
            true
        }

        Assert.assertEquals(presenter.addSeriesFavoriteToListLocal(series), true)
    }

    @Test
    fun `add series fail`() {
        every {
            repository.addSeriesToFavoriteList(series)
        } answers {
            false
        }

        Assert.assertEquals(presenter.addSeriesFavoriteToListLocal(series), false)
    }

    @Test
    fun `remove series success`() {
        every {
            repository.removeSeriesFromFavoriteList(id)
        } answers {
            true
        }

        Assert.assertEquals(presenter.removeSeriesFavoriteToListLocal(id), true)
    }

    @Test
    fun `remove series failure`() {
        val id = 2020222
        every {
            repository.removeSeriesFromFavoriteList(id)
        } answers {
            false
        }

        Assert.assertEquals(presenter.removeSeriesFavoriteToListLocal(id), false)
    }


    @Test
    fun `get instance test`() {
        val presenter = SeriesPresenter.getInstance(repository)
        val secondPresenter = SeriesPresenter.getInstance(repository)
        Assert.assertEquals(presenter, secondPresenter)
    }
}
