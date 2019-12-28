package com.adcenter.ui.controllers

import android.os.Handler
import android.view.View
import android.widget.ImageButton
import com.adcenter.R
import com.adcenter.data.Callable
import com.adcenter.data.NetworkDataRequest
import com.adcenter.data.getShowHideUrl
import com.adcenter.data.processors.ShowHideProcessor
import com.adcenter.extensions.toast
import java.util.concurrent.Executors

class ShowHideButtonController(
    private val button: ImageButton,
    private val id: String,
    private var isShown: Boolean
) : View.OnClickListener {

    override fun onClick(v: View?) {
        showHideAd()
    }

    private val executor = Executors.newCachedThreadPool()
    private val handler = Handler()

    private val showIcon = R.drawable.ic_button_show_ad
    private val hideIcon = R.drawable.ic_button_hide_ad

    init {
        updateIcon()
    }

    private fun showHideAd() {
        button.isEnabled = false

        executor.execute {
            try {
                val response = Callable<Boolean>()
                    .setRequest(NetworkDataRequest(getShowHideUrl(id)))
                    .setProcessor(ShowHideProcessor())
                    .call()

                if (response) {
                    handler.post {
                        isShown = !isShown
                        updateIcon()
                        button.isEnabled = true
                    }
                } else {
                    handler.post {
                        button.isEnabled = true
                    }
                }
            } catch (e: Exception) {
                handler.post {
                    button.isEnabled = true
                    button.context.toast(e.message ?: "Error")
                }
            }
        }
    }

    private fun updateIcon() {
        button.setImageResource(
            if (isShown) {
                hideIcon
            } else {
                showIcon
            }
        )
    }
}