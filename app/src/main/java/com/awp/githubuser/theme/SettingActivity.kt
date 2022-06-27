package com.awp.githubuser.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.awp.githubuser.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private val preference by lazy { ThemePreference(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        switch_dark.isChecked = preference.getBoolean("preference_theme")

        switch_dark.setOnCheckedChangeListener { compoundButton, isChecked ->
            when(isChecked) {
                true -> {
                    preference.put("preference_theme", true)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                false -> {
                    preference.put("preference_theme", false)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }
}