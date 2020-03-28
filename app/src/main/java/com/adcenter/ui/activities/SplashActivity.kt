package com.adcenter.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adcenter.config.IAppConfig
import com.adcenter.di.dagger.injector.Injector
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var appConfig: IAppConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector.appComponent.inject(this)
        initAppConfig()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun initAppConfig() {
        appConfig.initConfig()
    }
}