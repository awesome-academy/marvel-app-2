package com.nguyennhatminh614.marvelapp.screen.character

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.nguyennhatminh614.marvelapp.util.constant.Constant

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

    private var adapter: CharacterAdapter = CharacterAdapter()

    override fun initData() {
        characterPresenter.onStart()
        viewBinding.recyclerView.adapter = adapter
    }

    override fun onSuccessGetFavoriteItem(listCharacter: MutableList<Character>?) {
        listCharacter?.let { listFavoriteCharacterLocal.addAll(it) }
        activity?.runOnUiThread {
            adapter.updateFavoriteItem(listFavoriteCharacterLocal)
        }
    }

    override fun onSuccessGetDataFromRemote(listCharacter: MutableList<Character>?) {
        listCharacter?.let { listRemoteCharacter.addAll(it) }
        activity?.runOnUiThread {
            adapter.updateItemList(listRemoteCharacter)
        }
    }

    override fun onSuccessGetDataWithOffsetFromRemote(listCharacterOffset: MutableList<Character>?) {
        listCharacterOffset?.let { listRemoteCharacter.addAll(it) }
        activity?.runOnUiThread {
            adapter.updateItemList(listRemoteCharacter)
        }
    }

    override fun showLoadingDialog() {
        viewBinding.progressBarLoading.isVisible = true
    }

    override fun hideLoadingDialog() {
        viewBinding.progressBarLoading.isVisible = false
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(context, exception?.message, Toast.LENGTH_SHORT).show()
    }

    override fun initialize() {
        characterPresenter.setView(this)
    }

    override fun callData() {
        // Not support
    }

    override fun initEvent() {
        adapter.apply {
            registerClickFavoriteItemInterface(
                object : OnClickFavoriteItemInterface<Character> {
                    override fun onFavoriteItem(item: Character) {
                        characterPresenter.addCharacterFavoriteToListLocal(item)
                    }

                    override fun onUnfavoriteItem(item: Character) {
                        characterPresenter.removeCharacterFavoriteToListLocal(item.id)
                    }
                }
            )

            registerClickItemInterface(
                object : OnClickItemInterface<Character> {
                    override fun onClickItem(item: Character) {
                        findNavController().navigate(R.id.action_nav_character_to_nav_detail_character,
                            bundleOf(Constant.DETAIL_ITEM to item))
                    }
                }
            )
        }

        viewBinding.recyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    (recyclerView.layoutManager as? LinearLayoutManager)?.let {
                        if (it.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                            val offset = adapter.itemCount
                            characterPresenter.getCharacterListRemoteWithOffset(offset)
                        }
                    }
                }
            }
        )
    }
}
