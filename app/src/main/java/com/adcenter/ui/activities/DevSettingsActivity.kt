package com.adcenter.ui.activities

import android.os.Bundle
import com.adcenter.R
import com.adcenter.app.App
import com.adcenter.config.AppConfig
import com.adcenter.config.BackendUrlHolder
import kotlinx.android.synthetic.main.activity_dev_settings.*
import javax.inject.Inject

class DevSettingsActivity : BaseActivity() {

    @Inject
    lateinit var appConfig: AppConfig

    @Inject
    lateinit var urlHolder: BackendUrlHolder

    init {
        App.appComponent.inject(this)
    }

    override val layout: Int = R.layout.activity_dev_settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()

        backendUrlEditText.setText(urlHolder.url)
        saveButton.setOnClickListener { saveInfo() }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun saveInfo() {
        urlHolder.url = backendUrlEditText.text.toString()
        appConfig.backendUrl = backendUrlEditText.text.toString()
        finish()
    }
}