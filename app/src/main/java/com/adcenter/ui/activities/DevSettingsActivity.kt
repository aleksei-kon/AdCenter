package com.adcenter.ui.activities

import android.os.Bundle
import com.adcenter.R
import com.adcenter.appconfig.IAppConfig
import com.adcenter.appconfig.IBackendUrlHolder
import com.adcenter.di.dagger.injector.Injector
import kotlinx.android.synthetic.main.activity_dev_settings.*
import javax.inject.Inject

class DevSettingsActivity : BaseActivity() {

    @Inject
    lateinit var appConfig: IAppConfig

    @Inject
    lateinit var urlHolder: IBackendUrlHolder

    init {
        Injector.appComponent.inject(this)
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
        finish()
    }
}