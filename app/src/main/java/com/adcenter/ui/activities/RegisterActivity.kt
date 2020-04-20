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
import com.adcenter.features.registration.models.RegistrationRequestParams
import com.adcenter.features.registration.uistate.Error
import com.adcenter.features.registration.uistate.Success
import com.adcenter.features.registration.uistate.WaitRegistration
import com.adcenter.features.registration.viewmodel.RegistrationViewModel
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        provideViewModel(
            RegistrationViewModel::class.java,
            viewModelFactory
        )
    }

    override val layout: Int = R.layout.activity_register

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector
            .appComponent
            .registrationComponent()
            .inject(this)

        buttonRegister.setOnClickListener {
            register()
        }

        setViewModelObserver()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()

        super.onBackPressed()
    }

    private val isFieldsEmpty: Boolean
        get() = loginEditText.isEmpty ||
                passwordEditText.isEmpty ||
                passwordRepeatEditText.isEmpty

    private val isHasWhitespace: Boolean
        get() = loginEditText.contains(SPACE) ||
                passwordEditText.contains(SPACE) ||
                passwordRepeatEditText.contains(SPACE)

    private val isCorrectLength: Boolean
        get() = loginEditText.length in LOGIN_AND_PASSWORD_LENGTH_RANGE &&
                passwordEditText.length in LOGIN_AND_PASSWORD_LENGTH_RANGE &&
                passwordRepeatEditText.length in LOGIN_AND_PASSWORD_LENGTH_RANGE

    private val isPasswordsNotMatch: Boolean
        get() = passwordEditText.text.toString() != passwordRepeatEditText.text.toString()

    private fun register() {
        when {
            isFieldsEmpty -> longToast(getString(R.string.EMPTY_FIELDS_MESSAGE))
            isHasWhitespace -> longToast(getString(R.string.FIELDS_WITH_WHITESPACE_MESSAGE))
            !isCorrectLength -> longToast(getString(R.string.INCORRECT_PASSWORD_RANGE_MESSAGE))
            isPasswordsNotMatch -> longToast(getString(R.string.PASSWORDS_NOT_MATCH_MESSAGE))
            else -> {
                val params = RegistrationRequestParams(
                    credentialsModel = CredentialsModel(
                        username = loginEditText.text.toString(),
                        password = passwordEditText.text.toString()
                    )
                )

                viewModel.register(params)
            }
        }
    }

    private fun setViewModelObserver() {
        viewModel.registrationData.observe(this, Observer {
            when (it) {
                is WaitRegistration -> {
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