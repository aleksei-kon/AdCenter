package com.adcenter.ui.theme

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.adcenter.R
import com.adcenter.ui.theme.ThemeManagerConstants.THEME_MODE
import com.adcenter.ui.theme.ThemeManagerConstants.THEME_PREFERENCES

class ThemeManager(context: Context) : IThemeManager {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(THEME_PREFERENCES, Context.MODE_PRIVATE)

    private val isNightMode: Boolean
        get() = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

    init {
        setupThemeMode()
    }

    private fun setupThemeMode() {
        val mode = if (preferences.contains(THEME_MODE)) {
            preferences.getInt(THEME_MODE, AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun switchTheme() {
        val mode = if (isNightMode) {
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
        }

        AppCompatDelegate.setDefaultNightMode(mode)

        preferences.edit()
            .putInt(THEME_MODE, mode)
            .apply()
    }

    override fun getThemeResId(): Int =
        if (isNightMode) {
            R.style.AppDarkTheme
        } else {
            R.style.AppTheme
        }
}