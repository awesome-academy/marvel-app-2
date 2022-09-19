package com.nguyennhatminh614.marvelapp.screen.favorite

import android.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.nguyennhatminh614.marvelapp.R
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
import com.nguyennhatminh614.marvelapp.data.repository.source.local.character.CharacterLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.local.comic.ComicLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.CharacterDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.ComicDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.SeriesDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.StoriesDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.local.favorite.FavoriteLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.local.series.SeriesLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.local.stories.StoriesLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.favorite.FavoriteRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDrawerFavoriteBinding
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.OnLongClickItemInterface
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.constant.Constant

class FavoriteFragment :
    BaseFragment<FragmentDrawerFavoriteBinding>(FragmentDrawerFavoriteBinding::inflate),
    FavoriteContract.View {

    private var listFavoriteItem = mutableListOf<Any>()

    private val adapter = FavoriteAdapter()

    private val favoritePresenter by lazy {
        FavoritePresenter.getInstance(
            FavoriteRepository.getInstance(
                FavoriteLocalDataSource.getInstance(
                    CharacterLocalDataSource.getInstance(
                        CharacterDAOImpl.getInstance(
                            LocalDatabase.getInstance(context)
                        )
                    ),
                    ComicLocalDataSource.getInstance(
                        ComicDAOImpl.getInstance(
                            LocalDatabase.getInstance(context)
                        )
                    ),
                    SeriesLocalDataSource.getInstance(
                        SeriesDAOImpl.getInstance(
                            LocalDatabase.getInstance(context)
                        )
                    ),
                    StoriesLocalDataSource.getInstance(
                        StoriesDAOImpl.getInstance(
                            LocalDatabase.getInstance(context)
                        )
                    )
                ),
                FavoriteRemoteDataSource.getInstance()
            )
        )
    }

    override fun initData() {
        favoritePresenter.onStart()
        viewBinding.recyclerViewFavorite.adapter = adapter
    }

    override fun initialize() {
        favoritePresenter.setView(this)
    }

    override fun callData() {
        //Not support
    }

    override fun initEvent() {
        adapter.apply {
            registerClickItemInterface(
                object : OnClickItemInterface<FavoriteItem> {
                    override fun onClickItem(item: FavoriteItem) {
                        when (item.favoriteItemType) {
                            ComicEntry.COMIC_ENTITY -> favoritePresenter.getComicInfoByID(item.id)
                            CharacterEntry.CHARACTER_ENTITY -> favoritePresenter.getCharacterInfoByID(
                                item.id)
                            SeriesEntry.SERIES_ENTITY -> favoritePresenter.getSeriesInfoByID(item.id)
                            StoriesEntry.STORIES_ENTITY -> favoritePresenter.getStoriesInfoByID(item.id)
                            else -> null
                        }
                    }
                }
            )

            registerLongClickItemInterface(
                object : OnLongClickItemInterface<FavoriteItem> {
                    override fun onLongClickItem(data: FavoriteItem) {
                        AlertDialog.Builder(context).apply {
                            setTitle(TITLE)
                            setMessage(MESSAGE)
                            setCancelable(false)
                            setPositiveButton(POSITIVE_BUTTON) { _, _ ->
                                listFavoriteItem.remove(data)
                                favoritePresenter.removeItemFromFavoriteList(data)
                                adapter.updateDataItem(listFavoriteItem)
                            }
                            setNegativeButton(NEGATIVE_BUTTON) { dialog, _ ->
                                dialog.cancel()
                            }
                        }.create().show()
                    }
                }
            )
        }
    }

    override fun onSuccessGetFavoriteList(data: MutableList<Any>) {
        this.listFavoriteItem.addAll(data)
        activity?.runOnUiThread {
            adapter.updateDataItem(listFavoriteItem)
        }
    }

    override fun <T> onSuccessGetDetailData(data: T) {
        when (data) {
            is Character -> findNavController().navigate(R.id.action_nav_favorite_to_nav_detail_character,
                bundleOf(Constant.DETAIL_ITEM to data))
            is Comic -> findNavController().navigate(R.id.action_nav_favorite_to_nav_detail_comic,
                bundleOf(Constant.DETAIL_ITEM to data))
            is Series -> findNavController().navigate(R.id.action_nav_favorite_to_nav_detail_series,
                bundleOf(Constant.DETAIL_ITEM to data))
            is Stories -> findNavController().navigate(R.id.action_nav_favorite_to_nav_detail_stories,
                bundleOf(Constant.DETAIL_ITEM to data))
        }
    }

    override fun showLoadingDialog() {
        viewBinding.progressBarLoading.isVisible = true
    }

    override fun hideLoadingDialog() {
        viewBinding.progressBarLoading.isVisible = false
    }

    override fun onError(exception: Exception) {
        // Not support
    }

    companion object {
        const val TITLE = "Warning"
        const val MESSAGE = "Do you want to delete this item?"
        const val POSITIVE_BUTTON = "Yes"
        const val NEGATIVE_BUTTON = "No"
    }
}
