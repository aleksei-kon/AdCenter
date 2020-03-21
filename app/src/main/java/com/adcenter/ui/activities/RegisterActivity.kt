package com.adcenter.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.adcenter.R
import com.adcenter.extensions.*
import com.adcenter.features.registration.data.RegistrationRequestParams
import com.adcenter.features.registration.uistate.RegistrationUiState
import com.adcenter.features.registration.viewmodel.RegistrationViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_register

    private val viewModel by lazy {
        provideViewModel(RegistrationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            isFieldsEmpty -> longToast(getString(R.string.empty_fields_message))
            !isPasswordsTheSame -> longToast(getString(R.string.passwords_not_match_message))
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