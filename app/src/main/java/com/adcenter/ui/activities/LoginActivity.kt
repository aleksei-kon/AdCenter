package com.adcenter.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.adcenter.R
import com.adcenter.extensions.gone
import com.adcenter.extensions.setChildsEnabled
import com.adcenter.extensions.toast
import com.adcenter.extensions.visible
import com.adcenter.features.login.LoginConstants.LOGIN_SCOPE_ID
import com.adcenter.features.login.data.LoginRequestParams
import com.adcenter.features.login.uistate.LoginUiState
import com.adcenter.features.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class LoginActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_login

    private val activityScope =
        getKoin().getOrCreateScope(LOGIN_SCOPE_ID, named<LoginActivity>())

    private val viewModel: LoginViewModel = activityScope.get()

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
            toast(getString(R.string.empty_fields_message))
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
                    it.throwable.message?.let { message -> toast(message) }
                }
            }
        })
    }
}
