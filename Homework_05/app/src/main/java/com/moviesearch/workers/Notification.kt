package com.moviesearch.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.moviesearch.App.Companion.appContext
import com.moviesearch.MainActivity
import com.moviesearch.NOTIFICATION_CHANNEL_DESCRIPTION
import com.moviesearch.R
import com.moviesearch.WMTAG
import okhttp3.internal.notify

object Notification {
    private val channelName = appContext.getString(R.string.app_name)
    private val channelId = "${appContext.packageName} - $channelName"
    fun createNotificationChanel(){
        val channel = NotificationChannel(channelId, channelName, NotificationManagerCompat.IMPORTANCE_DEFAULT)
        channel.description = NOTIFICATION_CHANNEL_DESCRIPTION
        channel.setShowBadge(false)

        val notificationManager = appContext.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    fun createNotification(title: String, message: String,
                           bigText: String, autoCancel: Boolean, idKp: Int){
        val notificationBuilder = NotificationCompat.Builder(appContext, channelId).apply {
            setSmallIcon(R.drawable.theaters_48px)
            setContentTitle(title)
            setContentText(message)
            setChannelId(channelId)
            setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
            priority = NotificationCompat.PRIORITY_DEFAULT
            setAutoCancel(autoCancel)
            val intent = Intent(appContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("idKp", idKp)
            val pendingIntent = PendingIntent.getActivity(appContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            setContentIntent(pendingIntent)
        }
        //Log.d(WMTAG, "create notification")
        val notificationManager = NotificationManagerCompat.from(appContext)
        notificationManager.notify(102, notificationBuilder.build())
    }
}