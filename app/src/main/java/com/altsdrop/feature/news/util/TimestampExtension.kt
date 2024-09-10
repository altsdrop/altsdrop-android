package com.altsdrop.feature.news.util

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun convertFirebaseTimestamp(firebaseTimestamp: Timestamp): String {
    val currentTime = System.currentTimeMillis()
    val timestampInMillis = firebaseTimestamp.seconds * 1000 // Convert seconds to milliseconds
    val diffInMillis = currentTime - timestampInMillis

    // Check for different time ranges
    val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
    val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
    val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)
    val diffInWeeks = diffInDays / 7

    return when {
        diffInMinutes < 60 -> "${diffInMinutes}m"
        diffInHours < 24 -> "${diffInHours}h"
        diffInDays < 7 -> "${diffInDays}d"
        diffInWeeks < 52 -> "${diffInWeeks}w"
        else -> {
            // If more than a year, return the formatted date
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            dateFormat.format(Date(timestampInMillis))
        }
    }
}