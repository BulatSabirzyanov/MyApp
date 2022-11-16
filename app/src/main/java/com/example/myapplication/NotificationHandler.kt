package com.example.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService




class NotificationHandler(private val context: Context) {
    private var notificationManager: NotificationManager? = null

    fun createNotification(textTitle: String?, messegeSmallText: String?, messegeBigText: String?, intent: PendingIntent ){
        createNotificationChannel()
        val notificationBulder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(textTitle)
            .setContentText(messegeSmallText)
            .setSubText(messegeBigText)
            .setContentIntent(intent)
            .setCategory(Notification.CATEGORY_MESSAGE)
            .build()

        notificationManager?.notify(rand(), notificationBulder)
    }
    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name : CharSequence = "AlarmChannel"
            val description = "Channel for alarm"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }



    fun rand(): Int {
        require(1 <= 10000) { "Illegal Argument" }
        return (1..10000).shuffled().first()
    }

    companion object{
        const val CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
    }

}