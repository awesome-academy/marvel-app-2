package com.nguyennhatminh614.marvelapp.screen.loading

import android.content.Intent
import com.nguyennhatminh614.marvelapp.databinding.ActivityLaunchBinding
import com.nguyennhatminh614.marvelapp.screen.mainscreen.MainActivity
import com.nguyennhatminh614.marvelapp.util.base.BaseActivity
import java.util.Timer
import kotlin.concurrent.schedule

class LaunchActivity : BaseActivity() {
    private val binding by lazy { ActivityLaunchBinding.inflate(layoutInflater) }

    override fun initView() {
        setContentView(binding.root)

        supportActionBar?.hide()

        Timer().schedule(DELAY_TIME) {
            this@LaunchActivity.runOnUiThread {
                startActivity(Intent(this@LaunchActivity, MainActivity::class.java))
            }
        }
    }

    override fun initData() {
        /* TODO implement later */
    }

    companion object{
        private const val DELAY_TIME = 1200L
    }
}
