package com.altsdrop.core.util

import android.widget.Toast

interface ToastManager {
    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT)
}