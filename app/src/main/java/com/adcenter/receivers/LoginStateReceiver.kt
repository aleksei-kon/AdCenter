package com.adcenter.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

const val LOGIN_STATE_CHANGED_ACTION = "com.adcenter.receivers.LOGIN_STATE_CHANGED_ACTION"

class LoginStateReceiver(private val listener: () -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        listener.invoke()
    }
}