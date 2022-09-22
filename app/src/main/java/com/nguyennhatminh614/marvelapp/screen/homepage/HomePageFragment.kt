package com.nguyennhatminh614.marvelapp.screen.homepage

import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.CharacterEntry
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.model.CreatorEntry
import com.nguyennhatminh614.marvelapp.data.repository.HomePageRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.local.character.CharacterLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.LocalDatabase
import com.nguyennhatminh614.marvelapp.data.repository.source.local.database.dao.implementation.CharacterDAOImpl
import com.nguyennhatminh614.marvelapp.data.repository.source.local.homepage.HomePageLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.homepage.HomepageRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDrawerHomePageBinding
import com.nguyennhatminh614.marvelapp.util.OnClickFavoriteItemInterface
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.constant.Constant

class HomePageFragment :
    BaseFragment<FragmentDrawerHomePageBinding>(FragmentDrawerHomePageBinding::inflate),
    HomePageContract.View {

    private val listBannerUrl = mutableListOf<String>()

    private val listHomePageContent = mutableListOf<Any>()

    private val homePagePresenter by lazy {
        HomePagePresenter.getInstance(
            HomePageRepository.getInstance(
                HomePageLocalDataSource.getInstance(
                    CharacterLocalDataSource.getInstance(
                        CharacterDAOImpl.getInstance(
                            LocalDatabase.getInstance(context)
                        )
                    ),
                ),
                HomepageRemoteDataSource.getInstance()
            )
        )
    }

    private val adapter = HomePageAdapter()

    override fun initData() {
        homePagePresenter.getBannerUrlList()
        homePagePresenter.getFavoriteListCharacterLocal()
        homePagePresenter.getCharacterListRemote()
        homePagePresenter.getCreatorListRemote()
        viewBinding.recyclerViewHomePageContent.adapter = adapter
    }

    override fun initialize() {
        homePagePresenter.setView(this)
    }

    override fun callData() {
        // Not support
    }

    override fun initEvent() {
        for (linkUrl in listBannerUrl) {
            context?.let {
                val imageView = ImageView(it)
                Glide.with(it).load(linkUrl).error(R.drawable.character_image).into(imageView)
                viewBinding.viewFlipperBannerSlider.apply {
                    addView(imageView)
                    flipInterval = FLIP_INTERVAL
                    isAutoStart = true
                    setInAnimation(it, android.R.anim.slide_in_left)
                    setOutAnimation(it, android.R.anim.slide_out_right)
                }
            }
        }

        adapter.apply {
            registerClickFavoriteCharacterItemInterface(
                object : OnClickFavoriteItemInterface<Character> {
                    override fun onFavoriteItem(item: Character) {
                        homePagePresenter.addItemToFavoriteList(item)
                    }

                    override fun onUnfavoriteItem(item: Character) {
                        homePagePresenter.removeItemFromFavoriteList(item.id)
                    }
                }
            )

            registerClickItemCharacterInterface(
                object : OnClickItemInterface<Character> {
                    override fun onClickItem(item: Character) {
                        findNavController().navigate(R.id.action_nav_home_to_nav_detail_character,
                            bundleOf(Constant.DETAIL_ITEM to item))
                    }
                }
            )

            registerClickItemCreatorListener(
                object : OnClickItemInterface<Creator> {
                    override fun onClickItem(item: Creator) {
                        val bundle = bundleOf(Constant.DETAIL_ITEM to item)
                        findNavController().navigate(R.id.action_nav_home_to_nav_detail_creator,
                            bundle)
                    }
                }
            )
        }
    }

    override fun onSuccessGetBannerUrlList(data: List<String>?) {
        listBannerUrl.clear()
        data?.let { listBannerUrl.addAll(it) }
    }

    override fun <T : Any> onSuccessGetListRemote(data: MutableList<T>?) {
        val title = when(data?.get(Constant.FIRST_POSITION)) {
            is Character -> CharacterEntry.CHARACTER_ENTITY
            is Creator -> CreatorEntry.CREATOR_ENTITY
            else -> ""
        }

        listHomePageContent.add(title)
        data?.let { listHomePageContent.addAll(it) }

        activity?.runOnUiThread {
            adapter.updateDataItem(listHomePageContent)
        }
    }

    override fun onSuccessGetCharacterFavoriteListLocal(data: MutableList<Character>?) {
        activity?.runOnUiThread {
            data?.let { adapter.updateDataListCharacterFavorite(it) }
        }
    }

    override fun onError(exception: Exception?) {
        // Not support
    }

    override fun showLoadingDialog() {
        viewBinding.progressBarLoading.isVisible = true
    }

    override fun hideLoadingDialog() {
        viewBinding.progressBarLoading.isVisible = false
    }

    companion object {
        const val FLIP_INTERVAL = 4000
    }
}
