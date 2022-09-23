package com.nguyennhatminh614.marvelapp

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.repository.HomePageRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.screen.homepage.HomePageContract
import com.nguyennhatminh614.marvelapp.screen.homepage.HomePagePresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HomePagePresenterUnitTest {
    private val view = mockk<HomePageContract.View>(relaxed = true)
    private val repository = mockk<HomePageRepository>()
    private val exception = mockk<Exception>()
    private val listUrl = mockk<List<String>>()
    private val character = mockk<Character>()
    private val characterList = mockk<MutableList<Character>>()
    private val creatorList = mockk<MutableList<Creator>>()
    private val bannerListener = slot<OnResultListener<List<String>>>()
    private val characterListener = slot<OnResultListener<MutableList<Character>>>()
    private val creatorListener = slot<OnResultListener<MutableList<Creator>>>()
    private val presenter by lazy { HomePagePresenter(repository) }
    private val id = 2020222

    @Before
    fun setUp() {
        presenter.setView(view)
    }

    @Test
    fun `get banner list url success`() {
        every {
            repository.getBannerUrlList(capture(bannerListener))
        } answers {
            bannerListener.captured.onSuccess(listUrl)
        }

        presenter.getBannerUrlList()

        verify {
            view.onSuccessGetBannerUrlList(listUrl)
        }
    }

    @Test
    fun `get banner list url failure`() {
        every {
            repository.getBannerUrlList(capture(bannerListener))
        } answers {
            bannerListener.captured.onError(exception)
        }

        presenter.getBannerUrlList()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `get character list from remote success`() {
        every {
            repository.getCharacterListRemote(capture(characterListener))
        } answers {
            characterListener.captured.onSuccess(characterList)
        }

        presenter.getCharacterListRemote()

        verify {
            view.onSuccessGetListRemote(characterList)
        }
    }

    @Test
    fun `get character list from remote failure`() {
        every {
            repository.getCharacterListRemote(capture(characterListener))
        } answers {
            characterListener.captured.onError(exception)
        }

        presenter.getCharacterListRemote()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `get creator list from remote success`() {
        every {
            repository.getCreatorListRemote(capture(creatorListener))
        } answers {
            creatorListener.captured.onSuccess(creatorList)
        }

        presenter.getCreatorListRemote()

        verify {
            view.onSuccessGetListRemote(creatorList)
        }
    }

    @Test
    fun `get creator list from remote failure`() {
        every {
            repository.getCreatorListRemote(capture(creatorListener))
        } answers {
            creatorListener.captured.onError(exception)
        }

        presenter.getCreatorListRemote()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `get favorite list character local success`() {
        every {
            repository.getFavoriteCharacterListLocal(capture(characterListener))
        } answers {
            characterListener.captured.onSuccess(characterList)
        }

        presenter.getFavoriteListCharacterLocal()

        verify {
            view.onSuccessGetCharacterFavoriteListLocal(characterList)
        }
    }

    @Test
    fun `get favorite list character local fail`() {
        every {
            repository.getFavoriteCharacterListLocal(capture(characterListener))
        } answers {
            characterListener.captured.onError(exception)
        }

        presenter.getFavoriteListCharacterLocal()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `add character success`() {
        every {
            repository.addCharacterItemToFavoriteList(character)
        } answers {
            true
        }

        Assert.assertEquals(presenter.addItemToFavoriteList(character), true)
    }

    @Test
    fun `add character fail`() {
        every {
            repository.addCharacterItemToFavoriteList(character)
        } answers {
            false
        }

        Assert.assertEquals(presenter.addItemToFavoriteList(character), false)
    }

    @Test
    fun `remove character success`() {
        every {
            repository.removeCharacterItemFromListLocal(id)
        } answers {
            true
        }

        Assert.assertEquals(presenter.removeItemFromFavoriteList(id), true)
    }

    @Test
    fun `remove character failure`() {
        every {
            repository.removeCharacterItemFromListLocal(id)
        } answers {
            false
        }

        Assert.assertEquals(presenter.removeItemFromFavoriteList(id), false)
    }

    @Test
    fun `get instance is unique`() {
        val presenter = HomePagePresenter.getInstance(repository)
        val secondPresenter = HomePagePresenter.getInstance(repository)
        Assert.assertEquals(presenter, secondPresenter)
    }
}
