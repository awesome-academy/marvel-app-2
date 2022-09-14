package com.nguyennhatminh614.marvelapp.screen.settings

import androidx.appcompat.app.AppCompatActivity
import com.nguyennhatminh614.marvelapp.databinding.FragmentDrawerSettingsBinding
import com.nguyennhatminh614.marvelapp.screen.mainscreen.MainActivity
import com.nguyennhatminh614.marvelapp.util.OnSwitchDarkMode
import com.nguyennhatminh614.marvelapp.util.base.BaseFragment
import com.nguyennhatminh614.marvelapp.util.constant.Constant

class SettingsFragment :
    BaseFragment<FragmentDrawerSettingsBinding>(FragmentDrawerSettingsBinding::inflate) {

    private var onSwitchDarkMode: OnSwitchDarkMode? = null

    override fun initData() {
        val isNightModeOn = context?.getSharedPreferences(Constant.SHARED_PREFERENCE_FILE,
            AppCompatActivity.MODE_PRIVATE)?.getBoolean(MainActivity.NIGHT_MODE, false) ?: false
        viewBinding.btnSwitchDarkMode.isChecked = isNightModeOn
    }

    override fun initialize() {
        // Not support
    }

    override fun callData() {
        // Not support
    }

    override fun initEvent() {
        viewBinding.btnSwitchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                onSwitchDarkMode?.onTurnOnDarkMode()
            } else {
                onSwitchDarkMode?.onTurnOffDarkMode()
            }
        }
    }
}
