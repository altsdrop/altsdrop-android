package com.altsdrop.core.util

import androidx.compose.ui.graphics.Color

fun Color.toRgba(): String {
    val r = (red * 255).toInt()
    val g = (green * 255).toInt()
    val b = (blue * 255).toInt()
    val a = alpha
    return "rgba($r, $g, $b, $a)"
}