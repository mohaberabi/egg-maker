package com.example.android.eggtimernotifications.util

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.example.android.eggtimernotifications.MainActivity
import com.example.android.eggtimernotifications.R
import com.example.android.eggtimernotifications.receiver.SnoozeReceiver


object NotiConst {
    const val NOTIFICATION_ID = 0
    const val REQUEST_CODE = 0
    const val FLAGS = 0
}

fun NotificationManager.sendNotification(body: String, appContext: Context) {
    val contentIntent = Intent(appContext, MainActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        appContext,
        NotiConst.NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val eggImage = BitmapFactory.decodeResource(
        appContext.resources,
        R.drawable.cooked_egg
    )

    val bigPicStyle = NotificationCompat.BigPictureStyle().bigPicture(eggImage)
        .bigLargeIcon(null)


    val snoozeIntent = Intent(
        appContext,
        SnoozeReceiver::class.java
    )
    val snoozedPendingIntent: PendingIntent = PendingIntent.getBroadcast(
        appContext,
        NotiConst.REQUEST_CODE,
        snoozeIntent,
        PendingIntent.FLAG_IMMUTABLE
    )
    val builder = NotificationCompat.Builder(
        appContext,
        appContext.getString(R.string.egg_notification_channel_id)
    ).setSmallIcon(R.drawable.cooked_egg)
        .setContentTitle(appContext.getString(R.string.notification_title))
        .setContentText(body)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setStyle(bigPicStyle)
        .setLargeIcon(eggImage)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(
            R.drawable.egg_icon,
            appContext.getString(R.string.snooze),
            snoozedPendingIntent
        )
    notify(NotiConst.NOTIFICATION_ID, builder.build())


}


fun NotificationManager.cancelNotifications() {
    cancelAll()
}
