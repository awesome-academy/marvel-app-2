package com.nguyennhatminh614.marvelapp

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.CharacterEntry
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.ComicEntry
import com.nguyennhatminh614.marvelapp.data.model.FavoriteItem
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.model.SeriesEntry
import com.nguyennhatminh614.marvelapp.data.model.Stories
import com.nguyennhatminh614.marvelapp.data.model.StoriesEntry
import com.nguyennhatminh614.marvelapp.data.repository.FavoriteRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.screen.favorite.FavoriteContract
import com.nguyennhatminh614.marvelapp.screen.favorite.FavoritePresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FavoritePresenterUnitTest {
    private val view = mockk<FavoriteContract.View>(relaxed = true)
    private val repository = mockk<FavoriteRepository>()
    private val favoriteItem = mockk<FavoriteItem>()
    private val comic = mockk<Comic>()
    private val character = mockk<Character>()
    private val stories = mockk<Stories>()
    private val series = mockk<Series>()
    private val successComicData = mockk<MutableList<Comic>>()
    private val successCharacterData = mockk<MutableList<Character>>()
    private val successStoriesData = mockk<MutableList<Stories>>()
    private val successSeriesData = mockk<MutableList<Series>>()
    private val comicListener = slot<OnResultListener<Comic>>()
    private val storiesListener = slot<OnResultListener<Stories>>()
    private val seriesListener = slot<OnResultListener<Series>>()
    private val characterListener = slot<OnResultListener<Character>>()
    private val comicListListener = slot<OnResultListener<MutableList<Comic>>>()
    private val storiesListListener = slot<OnResultListener<MutableList<Stories>>>()
    private val seriesListListener = slot<OnResultListener<MutableList<Series>>>()
    private val characterListListener = slot<OnResultListener<MutableList<Character>>>()
    private val exception = mockk<Exception>()
    private val id = 520
    private val presenter by lazy { FavoritePresenter(repository) }

    @Before
    fun setUp() {
        presenter.setView(view)
    }

    @Test
    fun `get character favorite list local success`() {
        every {
            repository.getCharacterListLocal(capture(characterListListener))
        } answers {
            characterListListener.captured.onSuccess(successCharacterData)
        }

        presenter.getListCharacterFavorite()

        verify {
            view.onSuccessGetFavoriteList(successCharacterData)
        }
    }

    @Test
    fun `get character favorite list local failure`() {
        every {
            repository.getCharacterListLocal(capture(characterListListener))
        } answers {
            characterListListener.captured.onError(exception)
        }

        presenter.getListCharacterFavorite()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `get comic favorite list local success`() {
        every {
            repository.getComicListLocal(capture(comicListListener))
        } answers {
            comicListListener.captured.onSuccess(successComicData)
        }

        presenter.getListComicFavorite()

        verify {
            view.onSuccessGetFavoriteList(successComicData)
        }
    }

    @Test
    fun `get comic favorite list local failure`() {
        every {
            repository.getComicListLocal(capture(comicListListener))
        } answers {
            comicListListener.captured.onError(exception)
        }

        presenter.getListComicFavorite()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `get series favorite list local success`() {
        every {
            repository.getSeriesListLocal(capture(seriesListListener))
        } answers {
            seriesListListener.captured.onSuccess(successSeriesData)
        }

        presenter.getListSeriesFavorite()

        verify {
            view.onSuccessGetFavoriteList(successSeriesData)
        }
    }

    @Test
    fun `get series favorite list local failure`() {
        every {
            repository.getSeriesListLocal(capture(seriesListListener))
        } answers {
            seriesListListener.captured.onError(exception)
        }

        presenter.getListSeriesFavorite()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `get stories favorite list local success`() {
        every {
            repository.getStoriesListLocal(capture(storiesListListener))
        } answers {
            storiesListListener.captured.onSuccess(successStoriesData)
        }

        presenter.getListStoriesFavorite()

        verify {
            view.onSuccessGetFavoriteList(successStoriesData)
        }
    }

    @Test
    fun `get stories favorite list local failure`() {
        every {
            repository.getStoriesListLocal(capture(storiesListListener))
        } answers {
            storiesListListener.captured.onError(exception)
        }

        presenter.getListStoriesFavorite()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `remove item from favorite list success`() {
        every {
            repository.removeItemFromLocal(favoriteItem)
        } returns true

        Assert.assertEquals(presenter.removeItemFromFavoriteList(favoriteItem), true)
    }

    @Test
    fun `remove item from favorite list failure`() {
        every {
            repository.removeItemFromLocal(favoriteItem)
        } returns false

        Assert.assertEquals(presenter.removeItemFromFavoriteList(favoriteItem), false)
    }

    @Test
    fun `get detail character info by ID success`() {
        every {
            repository.getCharacterListRemoteWithID(id, capture(characterListener))
        } answers {
            characterListener.captured.onSuccess(character)
        }

        presenter.getCategoryInfoByID(CharacterEntry.CHARACTER_ENTITY, id)

        verify {
            view.onSuccessGetDetailData(character)
        }
    }

    @Test
    fun `get detail character info by ID failure`() {
        every {
            repository.getCharacterListRemoteWithID(id, capture(characterListener))
        } answers {
            characterListener.captured.onError(exception)
        }

        presenter.getCategoryInfoByID(CharacterEntry.CHARACTER_ENTITY, id)

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `get detail comic info by ID success`() {
        every {
            repository.getComicListRemoteWithID(id, capture(comicListener))
        } answers {
            comicListener.captured.onSuccess(comic)
        }

        presenter.getCategoryInfoByID(ComicEntry.COMIC_ENTITY, id)

        verify {
            view.onSuccessGetDetailData(comic)
        }
    }

    @Test
    fun `get detail comic info by ID failure`() {
        every {
            repository.getComicListRemoteWithID(id, capture(comicListener))
        } answers {
            comicListener.captured.onError(exception)
        }

        presenter.getCategoryInfoByID(ComicEntry.COMIC_ENTITY, id)

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `get detail series info by ID success`() {
        every {
            repository.getSeriesListRemoteWithID(id, capture(seriesListener))
        } answers {
            seriesListener.captured.onSuccess(series)
        }

        presenter.getCategoryInfoByID(SeriesEntry.SERIES_ENTITY, id)

        verify {
            view.onSuccessGetDetailData(series)
        }
    }

    @Test
    fun `get detail series info by ID failure`() {
        every {
            repository.getSeriesListRemoteWithID(id, capture(seriesListener))
        } answers {
            seriesListener.captured.onError(exception)
        }

        presenter.getCategoryInfoByID(SeriesEntry.SERIES_ENTITY, id)

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `get detail stories info by ID success`() {
        every {
            repository.getStoriesListRemoteWithID(id, capture(storiesListener))
        } answers {
            storiesListener.captured.onSuccess(stories)
        }

        presenter.getCategoryInfoByID(StoriesEntry.STORIES_ENTITY, id)

        verify {
            view.onSuccessGetDetailData(stories)
        }
    }

    @Test
    fun `get detail stories info by ID failure`() {
        every {
            repository.getStoriesListRemoteWithID(id, capture(storiesListener))
        } answers {
            storiesListener.captured.onError(exception)
        }

        presenter.getCategoryInfoByID(StoriesEntry.STORIES_ENTITY, id)

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `get instance object is unique`() {
        val presenter = FavoritePresenter.getInstance(repository)
        val presenter1 = FavoritePresenter.getInstance(repository)
        Assert.assertEquals(presenter, presenter1)
    }
}
