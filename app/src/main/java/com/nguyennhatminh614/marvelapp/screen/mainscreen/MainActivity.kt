package com.nguyennhatminh614.marvelapp.screen.mainscreen

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.data.model.MenuDrawerItem
import com.nguyennhatminh614.marvelapp.databinding.ActivityMainBinding
import com.nguyennhatminh614.marvelapp.screen.character.CharacterFragment
import com.nguyennhatminh614.marvelapp.screen.comic.ComicFragment
import com.nguyennhatminh614.marvelapp.screen.creator.CreatorFragment
import com.nguyennhatminh614.marvelapp.screen.event.EventFragment
import com.nguyennhatminh614.marvelapp.screen.homepage.HomePageFragment
import com.nguyennhatminh614.marvelapp.screen.series.SeriesFragment
import com.nguyennhatminh614.marvelapp.screen.settings.SettingsFragment
import com.nguyennhatminh614.marvelapp.screen.stories.StoriesFragment
import com.nguyennhatminh614.marvelapp.util.ClickListener
import com.nguyennhatminh614.marvelapp.util.RecyclerTouchListener
import com.nguyennhatminh614.marvelapp.util.base.BaseActivity
import com.nguyennhatminh614.marvelapp.util.constant.Constant

class MainActivity : BaseActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val menuList = listOf(
        MenuDrawerItem(R.drawable.ic_home, Constant.MENU_HOME_PAGE),
        MenuDrawerItem(R.drawable.ic_character, Constant.MENU_CHARACTER),
        MenuDrawerItem(R.drawable.ic_comic, Constant.MENU_COMIC),
        MenuDrawerItem(R.drawable.ic_creator, Constant.MENU_CREATOR),
        MenuDrawerItem(R.drawable.ic_event, Constant.MENU_EVENT),
        MenuDrawerItem(R.drawable.ic_series, Constant.MENU_SERIES),
        MenuDrawerItem(R.drawable.ic_stories, Constant.MENU_STORIES),
        MenuDrawerItem(R.drawable.ic_settings, Constant.MENU_SETTINGS),
    )

    private val fragmentList = listOf(
        HomePageFragment.newInstance(),
        CharacterFragment.newInstance(),
        ComicFragment.newInstance(),
        CreatorFragment.newInstance(),
        EventFragment.newInstance(),
        SeriesFragment.newInstance(),
        StoriesFragment.newInstance(),
        SettingsFragment.newInstance(),
    )

    private lateinit var adapter: MenuItemAdapter

    override fun initView() {
        binding.apply {
            setContentView(root)
            setSupportActionBar(appBarBase.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)

            updateAdapter(Constant.FIRST_POSITION)
            replaceFragment(fragmentList[Constant.FIRST_POSITION])

            appBarBase.toolbar.setNavigationOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }

            navigationBar.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)

                addOnItemTouchListener(
                    RecyclerTouchListener().apply {
                        setContext(this@MainActivity)
                        registerClickNavigationDrawerItem(
                            object : ClickListener {
                                override fun onClick(view: View, position: Int) {
                                    updateAdapter(position)
                                    replaceFragment(fragmentList[position])
                                    drawerLayout.closeDrawer(GravityCompat.START)
                                }
                            }
                        )
                    }
                )
            }
        }
    }

    override fun initData() {
        /* TODO implement later */
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.base, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_btn -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateAdapter(highlightItemPos: Int) {
        adapter = MenuItemAdapter(menuList, highlightItemPos)
        binding.navigationBar.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_base, fragment)
            .commit()
    }
}
