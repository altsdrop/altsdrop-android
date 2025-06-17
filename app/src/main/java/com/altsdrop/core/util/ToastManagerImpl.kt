package com.altsdrop.core.util

import android.content.Context
import android.widget.Toast
import javax.inject.Inject

class ToastManagerImpl @Inject constructor(private val context: Context) : ToastManager {
    override fun showToast(message: String, duration: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}