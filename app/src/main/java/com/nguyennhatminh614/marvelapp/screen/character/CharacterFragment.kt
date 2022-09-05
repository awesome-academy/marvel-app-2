package com.nguyennhatminh614.marvelapp.screen.character

import android.widget.Toast
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.repository.CharacterRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.local.character.CharacterLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.CharacterDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.character.CharacterRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDrawerCharacterBinding
import com.nguyennhatminh614.marvelapp.util.OnClickFavoriteItemInterface
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment

class CharacterFragment :
    BaseFragment<FragmentDrawerCharacterBinding>(FragmentDrawerCharacterBinding::inflate),
    CharacterContract.View {

    private val listFavoriteCharacterLocal = mutableListOf<Character>()
    private val listRemoteCharacter = mutableListOf<Character>()

    private val characterPresenter by lazy {
        CharacterPresenter.getInstance(
            CharacterRepository.getInstance(
                CharacterLocalDataSource.getInstance(
                    CharacterDAOImpl.getInstance(
                        LocalDatabase.getInstance(context)
                    )
                ),
                CharacterRemoteDataSource.getInstance()
            )
        )
    }

    private lateinit var adapter: CharacterAdapter

    override fun initData() {
        //not op
    }

    override fun onSuccessGetFavoriteItem(listCharacter: MutableList<Character>?) {
        listCharacter?.let { listFavoriteCharacterLocal.addAll(it) }

        activity?.runOnUiThread {
            adapter.updateFavoriteItem(listFavoriteCharacterLocal)
        }
    }

    override fun onSuccessGetDataFromRemote(listCharacter: MutableList<Character>?) {
        listCharacter?.let { listRemoteCharacter.addAll(it) }
        adapter.updateItemList(listRemoteCharacter)
        viewBinding.recyclerView.adapter = adapter
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception?.message, Toast.LENGTH_SHORT).show()
    }

    override fun initialize() {
        characterPresenter.setView(this)
        adapter = CharacterAdapter().apply {
            registerClickFavoriteItemInterface(
                object : OnClickFavoriteItemInterface<Character> {
                    override fun onFavoriteItem(item: Character) {
                        val checkExist = characterPresenter.checkFavoriteItemExist(item)
                        if (checkExist != null && checkExist == false) {
                            characterPresenter.addCharacterFavoriteToListLocal(item)
                        }
                    }

                    override fun onUnfavoriteItem(item: Character) {
                        characterPresenter.removeCharacterFavoriteToListLocal(item)
                    }
                }
            )

            registerClickItemInterface(
                object : OnClickItemInterface<Character> {
                    override fun onClickItem(item: Character) {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.nav_host_fragment_content_base, DetailCharacterFragment.newInstance(item))
                            .commit()
                    }
                }
            )
        }
    }

    override fun callData() {
        characterPresenter.getCharacterListRemote()
        characterPresenter.getCharacterListFromLocal()
    }

    companion object {
        fun newInstance() = CharacterFragment()
    }
}
