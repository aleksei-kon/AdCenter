package com.adcenter.ui.activities

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.adcenter.appconfig.IAppConfig
import com.adcenter.di.dagger.injector.Injector
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var appConfig: IAppConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector.appComponent.inject(this)
        initAppConfig()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE), 1
                )
            }
        }

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun initAppConfig() {
        appConfig.initConfig()
    }
}