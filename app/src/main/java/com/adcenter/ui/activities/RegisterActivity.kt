package com.adcenter.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adcenter.R
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.extensions.*
import com.adcenter.features.registration.data.RegistrationRequestParams
import com.adcenter.features.registration.uistate.RegistrationUiState
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
        get() = loginEditText.text.toString().isEmpty() ||
                passwordEditText.text.toString().isEmpty() ||
                passwordRepeatEditText.text.toString().isEmpty()

    private val isPasswordsTheSame: Boolean
        get() = passwordEditText.text.toString().isEmpty() == passwordRepeatEditText.text.toString().isEmpty()

    private fun register() {
        when {
            isFieldsEmpty -> longToast(getString(R.string.EMPTY_FIELDS_MESSAGE))
            !isPasswordsTheSame -> longToast(getString(R.string.PASSWORDS_NOT_MATCH_MESSAGE))
            else -> {
                val params = RegistrationRequestParams(
                    username = loginEditText.text.toString(),
                    password = passwordEditText.text.toString()
                )

                viewModel.register(params)
            }
        }
    }

    private fun setViewModelObserver() {
        viewModel.registrationData.observe(this, Observer {
            when (it) {
                is RegistrationUiState.WaitRegistration -> {
                    viewLayout.setChildsEnabled(false)
                    progressBar.visible()
                }
                is RegistrationUiState.Success -> {
                    viewLayout.setChildsEnabled(true)
                    progressBar.gone()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is RegistrationUiState.Error -> {
                    viewLayout.setChildsEnabled(true)
                    progressBar.gone()
                    it.throwable.message?.let { message -> longToast(message) }
                }
            }
        })
    }
}