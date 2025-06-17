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
        diffInMinutes <= 1 -> "Just now"
        diffInMinutes < 60 -> "$diffInMinutes mins ago"
        diffInHours.toInt() == 1 -> "$diffInHours hour ago"
        diffInHours < 24 -> "$diffInHours hours ago"
        diffInDays.toInt() == 1 -> "$diffInDays day ago"
        diffInDays < 7 -> "$diffInDays days ago"
        diffInWeeks.toInt() == 1 -> "$diffInWeeks week ago"
        diffInWeeks < 52 -> "$diffInWeeks weeks ago"
        else -> {
            // If more than a year, return the formatted date
            val dateFormat = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
            dateFormat.format(Date(timestampInMillis))
        }
    }
}