package com.adcenter.ui.activities

import android.content.Intent
import android.os.Bundle
import com.adcenter.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_register

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        buttonRegister.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()

        super.onBackPressed()
    }
}