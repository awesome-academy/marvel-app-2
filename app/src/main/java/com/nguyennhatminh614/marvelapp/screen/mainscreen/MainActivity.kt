package com.nguyennhatminh614.marvelapp.screen.mainscreen

import android.view.Menu
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.nguyennhatminh614.marvelapp.R
import com.nguyennhatminh614.marvelapp.databinding.ActivityMainBinding
import com.nguyennhatminh614.marvelapp.util.base.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun initView() {
        binding.apply {
            setContentView(root)
            setSupportActionBar(appBarBase.toolbar)

            val drawerLayout: DrawerLayout = drawerLayout
            val navView: NavigationView = navView
            val navController = findNavController(R.id.nav_host_fragment_content_base)

            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_home,
                    R.id.nav_character,
                    R.id.nav_comic,
                    R.id.nav_creator,
                    R.id.nav_event,
                    R.id.nav_series,
                    R.id.nav_stories,
                    R.id.nav_settings,
                ),
                drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
    }

    override fun initData() {
        /*Todo not implemented*/
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_base)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
