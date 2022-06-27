package com.awp.githubuser.theme

import android.content.Context
import android.content.SharedPreferences

class ThemePreference(context: Context) {

    private val PREFERENCES_NAME = "githubUserDarkMode"
    private var githubUserDarkMode: SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        githubUserDarkMode = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        editor = githubUserDarkMode.edit()
    }

    fun getBoolean(key: String): Boolean {
        return githubUserDarkMode.getBoolean(key, false)
    }

    fun put(key: String, value: Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

}