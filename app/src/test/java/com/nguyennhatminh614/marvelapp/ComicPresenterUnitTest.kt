package com.nguyennhatminh614.marvelapp

import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.repository.ComicRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.screen.comic.ComicContract
import com.nguyennhatminh614.marvelapp.screen.comic.ComicPresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class ComicPresenterUnitTest {
    private val view = mockk<ComicContract.View>(relaxed = true)
    private val repository = mockk<ComicRepository>()
    private val successData = mock<MutableList<Comic>>()
    private val listener = slot<OnResultListener<MutableList<Comic>>>()
    private val exception = mockk<Exception>()
    private val offset = 20
    private val comic = mockk<Comic>()
    private val id = 2020222
    private val presenter by lazy { ComicPresenter(repository) }

    @Before
    fun setUp() {
        presenter.setView(view)
    }

    @Test
    fun `get all favorite list local success`() {
        every {
            repository.getAllFavoriteListLocal(capture(listener))
        } answers {
            listener.captured.onSuccess(successData)
        }

        presenter.getAllFavoriteListLocal()

        verify{
            view.onSuccessGetFavoriteItem(successData)
        }
    }

    @Test
    fun `get all favorite list local failure`() {
        every {
            repository.getAllFavoriteListLocal(capture(listener))
        } answers {
            listener.captured.onError(exception)
        }

        presenter.getAllFavoriteListLocal()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `check comic is exist in favorite list`() {
        every {
            repository.checkExistComic(comic)
        } answers {
            true
        }

        Assert.assertEquals(presenter.checkExistComic(comic), true)
    }

    @Test
    fun `check comic is not exist in favorite list`() {
        every {
            repository.checkExistComic(comic)
        } answers {
            false
        }

        Assert.assertEquals(presenter.checkExistComic(comic), false)
    }

    @Test
    fun `add comic to favorite list success`() {
        every {
            repository.addComicToFavoriteList(comic)
        } answers {
            true
        }

        Assert.assertEquals(presenter.addComicToFavoriteList(comic), true)
    }

    @Test
    fun `add comic to favorite list failure`() {
        every {
            repository.addComicToFavoriteList(comic)
        } answers {
            false
        }

        Assert.assertEquals(presenter.addComicToFavoriteList(comic), false)
    }

    @Test
    fun `remove comic to favorite list success`() {
        every {
            repository.removeComicFromFavoriteList(id)
        } answers {
            true
        }

        Assert.assertEquals(presenter.removeComicFromFavoriteList(id), true)
    }

    @Test
    fun `remove comic to favorite list failure`() {
        every {
            repository.removeComicFromFavoriteList(id)
        } answers {
            false
        }

        Assert.assertEquals(presenter.removeComicFromFavoriteList(id), false)
    }

    @Test
    fun `get remote list comic success`(){
        every {
            repository.getRemoteListComic(capture(listener))
        } answers {
            listener.captured.onSuccess(successData)
        }

        presenter.getRemoteListComic()

        verify {
            view.onSuccessGetDataFromRemote(successData)
        }
    }

    @Test
    fun `get remote list comic failure`(){
        every {
            repository.getRemoteListComic(capture(listener))
        } answers {
            listener.captured.onError(exception)
        }

        presenter.getRemoteListComic()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `get remote list comic with offset success`(){
        every {
            repository.getRemoteListComicWithOffset(offset,capture(listener))
        } answers {
            listener.captured.onSuccess(successData)
        }

        presenter.getComicListRemoteWithOffset(offset)

        verify {
            view.onSuccessGetDataWithOffsetFromRemote(successData)
        }
    }

    @Test
    fun `get remote list comic with offset failure`(){
        every {
            repository.getRemoteListComicWithOffset(offset,capture(listener))
        } answers {
            listener.captured.onError(exception)
        }

        presenter.getComicListRemoteWithOffset(offset)

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `check get instance presenter is unique`() {
        val presenter = ComicPresenter.getInstance(repository)
        val presenter1 = ComicPresenter.getInstance(repository)
        Assert.assertEquals(presenter, presenter1)
    }
}
