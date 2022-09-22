package com.nguyennhatminh614.marvelapp

import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.repository.CharacterRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.fetchjson.OnResultListener
import com.nguyennhatminh614.marvelapp.screen.character.CharacterContract
import com.nguyennhatminh614.marvelapp.screen.character.CharacterPresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class CharacterPresenterUnitTest {
    private val view = mockk<CharacterContract.View>(relaxed = true)
    private val repository = mockk<CharacterRepository>()
    private val successData = mock<MutableList<Character>>()
    private val listener = slot<OnResultListener<MutableList<Character>>>()
    private val exception = mockk<Exception>()
    private val offset = 20
    private val character = mockk<Character>()
    private val id = 2020222
    private val presenter by lazy { CharacterPresenter(repository) }

    @Before
    fun setUp() {
        presenter.setView(view)
    }

    @Test
    fun `get character list from local success`() {
        every {
            repository.getCharacterListLocal(capture(listener))
        } answers {
            listener.captured.onSuccess(successData)
        }

        presenter.getCharacterListFromLocal()

        verify {
            view.onSuccessGetFavoriteItem(successData)
        }
    }

    @Test
    fun `get character list from local failure`() {
        every {
            repository.getCharacterListLocal(capture(listener))
        } answers {
            listener.captured.onError(exception)
        }

        presenter.getCharacterListFromLocal()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun  `get character list remote success`() {
        every {
            repository.getCharacterListRemote(capture(listener))
        } answers {
            listener.captured.onSuccess(successData)
        }

        presenter.getCharacterListRemote()

        verify {
            view.onSuccessGetDataFromRemote(successData)
        }
    }

    @Test
    fun `get character list remote failure`() {
        every {
            repository.getCharacterListRemote(capture(listener))
        } answers {
            listener.captured.onError(exception)
        }

        presenter.getCharacterListRemote()

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `get character list remote with offset success`() {
        every {
            repository.getCharacterListRemoteWithOffset(offset, capture(listener))
        } answers {
            listener.captured.onSuccess(successData)
        }

        presenter.getCharacterListRemoteWithOffset(offset)

        verify {
            view.onSuccessGetDataWithOffsetFromRemote(successData)
        }
    }

    @Test
    fun `get character list remote with offset failure`() {
        every {
            repository.getCharacterListRemoteWithOffset(offset, capture(listener))
        } answers {
            listener.captured.onError(exception)
        }

        presenter.getCharacterListRemoteWithOffset(offset)

        verify {
            view.onError(exception)
        }
    }

    @Test
    fun `check character is exists in favorite list`() {
        every {
            repository.checkFavoriteCharacterExists(character)
        } returns true

       Assert.assertEquals(presenter.checkFavoriteItemExist(character), true)
    }

    @Test
    fun `check character is not exists in favorite list`() {
        every {
            repository.checkFavoriteCharacterExists(character)
        } answers {
            false
        }

        Assert.assertEquals(presenter.checkFavoriteItemExist(character), false)
    }

    @Test
    fun `add character success`() {
        every {
            repository.addCharacterFavoriteToListLocal(character)
        } answers {
            true
        }

        Assert.assertEquals(presenter.addCharacterFavoriteToListLocal(character), true)
    }

    @Test
    fun `add character fail`() {
        every {
            repository.addCharacterFavoriteToListLocal(character)
        } answers {
            false
        }

        Assert.assertEquals(presenter.addCharacterFavoriteToListLocal(character), false)
    }

    @Test
    fun `remove character success`() {
        every {
            repository.removeCharacterFavoriteToListLocal(id)
        } answers {
            true
        }

        Assert.assertEquals(presenter.removeCharacterFavoriteToListLocal(id), true)
    }

    @Test
    fun `remove character failure`() {
        val id = 2020222
        every {
            repository.removeCharacterFavoriteToListLocal(id)
        } answers {
            false
        }

        Assert.assertEquals(presenter.removeCharacterFavoriteToListLocal(id), false)
    }


    @Test
    fun `get instance test`() {
        val presenter = CharacterPresenter.getInstance(repository)
        val secondPresenter = CharacterPresenter.getInstance(repository)
        Assert.assertEquals(presenter, secondPresenter)
    }
}
