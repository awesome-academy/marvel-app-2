package com.nguyennhatminh614.marvelapp.screen.homepage

import android.util.Log
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.repository.HomePageRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.local.homepage.HomePageLocalDataSource
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.homepage.HomepageRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.FragmentDrawerHomePageBinding
import com.nguyennhatminh614.marvelapp.screen.character.CharacterAdapter
import com.nguyennhatminh614.marvelapp.screen.comic.ComicAdapter
import com.nguyennhatminh614.marvelapp.screen.creator.CreatorAdapter
import com.nguyennhatminh614.marvelapp.util.OnClickItemInterface
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment

class HomePageFragment :
    BaseFragment<FragmentDrawerHomePageBinding>(FragmentDrawerHomePageBinding::inflate),
    HomePageContract.View {

    private val listBannerUrl = mutableListOf<String>()
    private val listComic = mutableListOf<Comic>()
    private val listCharacter = mutableListOf<Character>()
    private val listCreator = mutableListOf<Creator>()

    private val comicAdapter = ComicAdapter()
    private val characterAdapter = CharacterAdapter()
    private val creatorAdapter = CreatorAdapter()

    private val homePagePresenter by lazy {
        HomePagePresenter.getInstance(
            HomePageRepository.getInstance(
                HomePageLocalDataSource.getInstance(),
                HomepageRemoteDataSource.getInstance()
            )
        )
    }

    override fun initData() {
        viewBinding.apply {
            recyclerViewComicList.adapter = comicAdapter
            recyclerViewCreatorList.adapter = creatorAdapter
            recyclerViewDetailCharacters.adapter = characterAdapter
        }
    }

    override fun initialize() {
        homePagePresenter.setView(this)
    }

    override fun callData() {
        homePagePresenter.onStart()
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

        comicAdapter.registerClickItemListener(
            object : OnClickItemInterface<Comic> {
                override fun onClickItem(item: Comic) {
                    val bundle = bundleOf("item" to item)
                    findNavController().navigate(R.id.action_nav_home_to_nav_detail_comic, bundle)
                }
            }
        )

        creatorAdapter.registerClickItemListener(
            object : OnClickItemInterface<Creator> {
                override fun onClickItem(item: Creator) {
                    val bundle = bundleOf("item" to item)
                    findNavController().navigate(R.id.action_nav_home_to_nav_detail_creator, bundle)
                }
            }
        )

        characterAdapter.registerClickItemInterface(
            object : OnClickItemInterface<Character> {
                override fun onClickItem(item: Character) {
                    val bundle = bundleOf("item" to item)
                    findNavController().navigate(R.id.action_nav_home_to_nav_detail_character, bundle)
                }
            }
        )
    }

    override fun onSuccessGetBannerUrlList(data: List<String>) {
        listComic.clear()
        listBannerUrl.addAll(data)
    }

    override fun onSuccessGetComicListRemote(data: MutableList<Comic>) {
        listComic.clear()
        listComic.addAll(data)
        Log.d("dataSize", listComic.size.toString())
        activity?.runOnUiThread {
            comicAdapter.updateListComic(listComic)
        }
    }

    override fun onSuccessGetCharacterListRemote(data: MutableList<Character>) {
        listCharacter.clear()
        listCharacter.addAll(data)

        activity?.runOnUiThread {
            characterAdapter.updateItemList(listCharacter)
        }
    }

    override fun onSuccessGetCreatorListRemote(data: MutableList<Creator>) {
        listCreator.clear()
        listCreator.addAll(data)

        activity?.runOnUiThread {
            creatorAdapter.updateItemData(listCreator)
        }
    }

    override fun onError(exception: Exception) {
        // Not support
    }

    companion object {
        const val FLIP_INTERVAL = 4000
        const val TAG_HOME_PAGE_FRAGMENT = "homePageFragmentTag"
        fun newInstance() = HomePageFragment()
    }
}
