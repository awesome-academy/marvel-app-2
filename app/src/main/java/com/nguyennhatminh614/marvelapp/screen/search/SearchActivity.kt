package com.nguyennhatminh614.marvelapp.screen.search

import com.google.android.material.chip.Chip
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.Character
import com.nguyennhatminh614.marvelapp.data.model.CharacterEntry
import com.nguyennhatminh614.marvelapp.data.model.Comic
import com.nguyennhatminh614.marvelapp.data.model.ComicEntry
import com.nguyennhatminh614.marvelapp.data.model.Creator
import com.nguyennhatminh614.marvelapp.data.model.CreatorEntry
import com.nguyennhatminh614.marvelapp.data.model.Event
import com.nguyennhatminh614.marvelapp.data.model.EventEntry
import com.nguyennhatminh614.marvelapp.data.model.SearchObject
import com.nguyennhatminh614.marvelapp.data.model.Series
import com.nguyennhatminh614.marvelapp.data.model.SeriesEntry
import com.nguyennhatminh614.marvelapp.data.repository.SearchRepository
import com.nguyennhatminh614.marvelapp.data.repository.source.remote.search.SearchRemoteDataSource
import com.nguyennhatminh614.marvelapp.databinding.ActivitySearchBinding
import com.nguyennhatminh614.marvelapp.screen.character.DetailCharacterFragment
import com.nguyennhatminh614.marvelapp.screen.comic.DetailComicFragment
import com.nguyennhatminh614.marvelapp.screen.creator.DetailCreatorFragment
import com.nguyennhatminh614.marvelapp.screen.event.DetailEventFragment
import com.nguyennhatminh614.marvelapp.screen.series.DetailSeriesFragment
import com.nguyennhatminh614.marvelapp.util.base.BaseActivity

class SearchActivity : BaseActivity(), SearchContract.View {

    private val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private val searchResultList = mutableListOf<SearchObject>()
    private val listAny = mutableListOf<Any>()
    private val adapter = SearchAdapter()
    private val listState = mutableListOf<Chip>()

    private val searchPresenter by lazy {
        SearchPresenter.getInstance(
            SearchRepository.getInstance(
                SearchRemoteDataSource.getInstance()
            )
        )
    }

    override fun initView() {
        searchPresenter.setView(this)
        binding.apply {
            setContentView(root)
            setSupportActionBar(toolBar)
            supportActionBar?.setDisplayShowTitleEnabled(false)

            recyclerViewSearchResult.adapter = adapter

            listState.addAll(
                arrayListOf(
                    chipComic,
                    chipCharacter,
                    chipEvents,
                    chipCreator,
                    chipSeries,
                )
            )
        }
    }

    override fun initData() {
        // Not support
    }

    override fun initEvent() {
        binding.toolBar.setNavigationOnClickListener {
            onBackPressed()
        }

        SearchActivityUtil.hideKeyboard(this, binding)
        SearchActivityUtil.queryTextString(
            searchPresenter,
            binding,
            listState,
            adapter
        ) {
            listAny.clear()
            searchResultList.clear()
        }

        adapter.registerClickItemInterface {
            val fragment = when(val data = listAny[it]) {
                is Comic -> DetailComicFragment.newInstance(data)
                is Character -> DetailCharacterFragment.newInstance(data)
                is Event -> DetailEventFragment.newInstance(data)
                is Creator -> DetailCreatorFragment.newInstance(data)
                is Series -> DetailSeriesFragment.newInstance(data)
                else -> null
            }

            fragment?.let { notNullFragment ->
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_content_base, notNullFragment)
                    .commit()
            }
        }
    }

    override fun onSuccessGetListCharacter(data: MutableList<Character>?) {
        data?.forEach {
            searchResultList.add(
                SearchObject(
                    id = it.id,
                    thumbnailLink = it.thumbnailLink,
                    category = CharacterEntry.CHARACTER_ENTITY,
                    title = it.name
                )
            )
        }

        data?.let { listAny.addAll(it) }

        adapter.updateDataItem(searchResultList)
    }

    override fun onSuccessGetListCreator(data: MutableList<Creator>?) {
        data?.forEach {
            searchResultList.add(
                SearchObject(
                    id = it.id,
                    thumbnailLink = it.thumbnailLink,
                    category = CreatorEntry.CREATOR_ENTITY,
                    title = it.name,
                )
            )
        }

        data?.let { listAny.addAll(it) }

        adapter.updateDataItem(searchResultList)
    }

    override fun onSuccessGetListComic(data: MutableList<Comic>?) {
        data?.forEach {
            searchResultList.add(
                SearchObject(
                    id = it.id,
                    thumbnailLink = it.thumbnailLink,
                    category = ComicEntry.COMIC_ENTITY,
                    title = it.title,
                )
            )
        }

        data?.let { listAny.addAll(it) }
        adapter.updateDataItem(searchResultList)
    }

    override fun onSuccessGetListEvent(data: MutableList<Event>?) {
        data?.forEach {
            searchResultList.add(
                SearchObject(
                    id = it.id,
                    thumbnailLink = it.thumbnailLink,
                    category = EventEntry.EVENT_ENTITY,
                    title = it.title,
                )
            )
        }

        data?.let { listAny.addAll(it) }
        adapter.updateDataItem(searchResultList)
    }

    override fun onSuccessGetListSeries(data: MutableList<Series>?) {
        data?.forEach {
            searchResultList.add(
                SearchObject(
                    id = it.id,
                    thumbnailLink = it.thumbnailLink,
                    category = SeriesEntry.SERIES_ENTITY,
                    title = it.title,
                )
            )
        }

        data?.let { listAny.addAll(it) }
        adapter.updateDataItem(searchResultList)
    }

    override fun onError(exception: Exception?) {
        // Not support
    }
}
