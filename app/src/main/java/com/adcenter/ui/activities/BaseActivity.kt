package com.adcenter.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adcenter.theme.IThemeManager
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {

    private val themeManager: IThemeManager by inject()

    protected abstract val layout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(themeManager.getThemeResId())
        setContentView(layout)
    }
}