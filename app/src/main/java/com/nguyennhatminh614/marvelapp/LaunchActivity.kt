package com.nguyennhatminh614.marvelapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nguyennhatminh614.marvelapp.databinding.ActivityLaunchBinding
import java.util.Timer
import kotlin.concurrent.schedule

class LaunchActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLaunchBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        Timer().schedule(3000) {
            this@LaunchActivity.runOnUiThread {
                startActivity(Intent(this@LaunchActivity, MainActivity::class.java))
            }
        }
    }
}
