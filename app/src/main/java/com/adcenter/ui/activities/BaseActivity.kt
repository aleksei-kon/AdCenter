package com.adcenter.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adcenter.app.App
import com.adcenter.theme.IThemeManager
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var themeManager: IThemeManager

    protected abstract val layout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        setTheme(themeManager.getThemeResId())
        setContentView(layout)
    }
}