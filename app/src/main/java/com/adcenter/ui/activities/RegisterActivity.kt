package com.adcenter.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.adcenter.R
import com.adcenter.extensions.gone
import com.adcenter.extensions.setChildsEnabled
import com.adcenter.extensions.toast
import com.adcenter.extensions.visible
import com.adcenter.features.registration.RegistrationConstants.REGISTRATION_SCOPE_ID
import com.adcenter.features.registration.data.RegistrationRequestParams
import com.adcenter.features.registration.uistate.RegistrationUiState
import com.adcenter.features.registration.viewmodel.RegistrationViewModel
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class RegisterActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_register

    private val activityScope =
        getKoin().getOrCreateScope(REGISTRATION_SCOPE_ID, named<RegisterActivity>())

    private val viewModel: RegistrationViewModel = activityScope.get()

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
            isFieldsEmpty -> toast(getString(R.string.empty_fields_message))
            !isPasswordsTheSame -> toast(getString(R.string.passwords_not_match_message))
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
                    it.throwable.message?.let { message -> toast(message) }
                }
            }
        })
    }
}