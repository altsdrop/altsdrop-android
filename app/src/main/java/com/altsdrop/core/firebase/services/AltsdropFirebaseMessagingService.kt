package com.altsdrop.core.firebase.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.altsdrop.R
import com.altsdrop.app.ui.activity.HomeActivity
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.net.URL

class AltsdropFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val deepLink = message.data["deep_link"] ?: ""
        val title = message.data["title"] ?: "AltsDrop"
        val body = message.data["body"] ?: ""
        val imageUrl = message.data["image_url"] ?: ""

        // Intent to open Compose deep link
        val intent = Intent(this, HomeActivity::class.java).apply {
            action = Intent.ACTION_VIEW
            data = deepLink.toUri()
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "altsdrop_notifications"
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    "AltsDrop Notifications",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        // Optional image
        imageUrl.takeIf { it.isNotBlank() }.let {
            try {
                val bitmap = BitmapFactory.decodeStream(URL(it).openStream())

                builder.setLargeIcon(bitmap)

                builder.setStyle(
                    NotificationCompat
                        .BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null as Bitmap?)
                )
            } catch (_: Exception) {
                FirebaseCrashlytics.getInstance().log(
                    "Failed to download notification image from URL: $imageUrl"
                )
            }
        }

        manager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}