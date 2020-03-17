package com.adcenter.ui.activities

import android.os.Bundle
import com.adcenter.R
import com.adcenter.config.AppConfig
import com.adcenter.config.BackendUrlHolder
import kotlinx.android.synthetic.main.activity_dev_settings.*
import org.koin.android.ext.android.inject

class DevSettingsActivity : BaseActivity() {

    private val urlHolder: BackendUrlHolder by inject()

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
        AppConfig.backendUrl = backendUrlEditText.text.toString()
        finish()
    }
}