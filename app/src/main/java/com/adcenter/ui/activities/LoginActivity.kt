package com.adcenter.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.adcenter.R
import com.adcenter.extensions.*
import com.adcenter.features.login.data.LoginRequestParams
import com.adcenter.features.login.uistate.LoginUiState
import com.adcenter.features.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_login

    private val viewModel by lazy {
        provideViewModel(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        buttonRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        buttonLogin.setOnClickListener {
            login()
        }

        setViewModelObserver()
    }

    private val isFieldsEmpty: Boolean
        get() = loginEditText.text.toString().isEmpty() ||
                passwordEditText.text.toString().isEmpty()

    private fun login() {
        if (isFieldsEmpty) {
            longToast(getString(R.string.EMPTY_FIELDS_MESSAGE))
        } else {
            val params = LoginRequestParams(
                username = loginEditText.text.toString(),
                password = passwordEditText.text.toString()
            )

            viewModel.login(params)
        }
    }

    private fun setViewModelObserver() {
        viewModel.loginData.observe(this, Observer {
            when (it) {
                is LoginUiState.WaitLogin -> {
                    viewLayout.setChildsEnabled(false)
                    progressBar.visible()
                }
                is LoginUiState.Success -> {
                    viewLayout.setChildsEnabled(true)
                    progressBar.gone()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is LoginUiState.Error -> {
                    viewLayout.setChildsEnabled(true)
                    progressBar.gone()
                    it.throwable.message?.let { message -> longToast(message) }
                }
            }
        })
    }
}
