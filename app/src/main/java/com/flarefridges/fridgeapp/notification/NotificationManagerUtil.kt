package com.flarefridges.fridgeapp.notification

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.flarefridges.fridgeapp.MainActivity
import com.flarefridges.fridgeapp.R

private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

fun NotificationManager.sendExpiryNotification(
    applicationContext: Context,
    message: String,
) {
    val contentIntent = Intent(applicationContext, MainActivity::class.java)

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.expiry_notification_channel_id)
    )
        .setSmallIcon(R.mipmap.ic_launcher_round)
        .setContentTitle(applicationContext.getString(R.string.expiry_notification_title))
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}