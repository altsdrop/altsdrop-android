package com.altsdrop.core.util

interface Resources {
    fun getString(resId: Int): String
    fun getString(resId: Int, vararg templateStringArgs : String) : String
}