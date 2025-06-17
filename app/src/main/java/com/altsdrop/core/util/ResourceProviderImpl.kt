package com.altsdrop.core.util

import android.content.Context
import javax.inject.Inject

class ResourcesImpl @Inject constructor(private val context: Context) : Resources {
    override fun getString(resId: Int) = context.getString(resId)
    override fun getString(resId: Int, vararg templateStringArgs: String): String =
        context.getString(resId, templateStringArgs)
}