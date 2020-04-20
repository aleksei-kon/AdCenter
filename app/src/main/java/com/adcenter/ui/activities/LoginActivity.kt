package com.adcenter.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adcenter.R
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.network.CredentialsModel
import com.adcenter.extensions.*
import com.adcenter.extensions.Constants.LOGIN_AND_PASSWORD_LENGTH_RANGE
import com.adcenter.extensions.Constants.SPACE
import com.adcenter.features.login.models.LoginRequestParams
import com.adcenter.features.login.uistate.Error
import com.adcenter.features.login.uistate.Success
import com.adcenter.features.login.uistate.WaitLogin
import com.adcenter.features.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_login

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        provideViewModel(
            LoginViewModel::class.java,
            viewModelFactory
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector
            .appComponent
            .loginComponent()
            .inject(this)

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
        get() = loginEditText.isEmpty ||
                passwordEditText.isEmpty

    private val isHasWhitespace: Boolean
        get() = loginEditText.contains(SPACE) ||
                passwordEditText.contains(SPACE)

    private val isCorrectLength: Boolean
        get() = loginEditText.length in LOGIN_AND_PASSWORD_LENGTH_RANGE &&
                passwordEditText.length in LOGIN_AND_PASSWORD_LENGTH_RANGE

    private fun login() {
        when {
            isFieldsEmpty -> longToast(getString(R.string.EMPTY_FIELDS_MESSAGE))
            isHasWhitespace -> longToast(getString(R.string.FIELDS_WITH_WHITESPACE_MESSAGE))
            else -> {
                val params = LoginRequestParams(
                    credentialsModel = CredentialsModel(
                        username = loginEditText.text.toString(),
                        password = passwordEditText.text.toString()
                    )
                )

                viewModel.login(params)
            }
        }
    }

    private fun setViewModelObserver() {
        viewModel.loginData.observe(this, Observer {
            when (it) {
                is WaitLogin -> {
                    viewLayout.setChildsEnabled(false)
                    progressBar.visible()
                }
                is Success -> {
                    viewLayout.setChildsEnabled(true)
                    progressBar.gone()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is Error -> {
                    viewLayout.setChildsEnabled(true)
                    progressBar.gone()
                    it.throwable.message?.let { message -> longToast(message) }
                }
            }
        })
    }
}
