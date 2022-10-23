package com.tawajood.snail.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

import com.tawajood.snail.R
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants

import java.util.*

private const val CHANNEL_ID = "my_channel"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.d("islam", "onNewToken: $s")
        PrefsHelper.setFCMToken(s)
        sendRegistrationToServer(s)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        sendBroadCast()
        showNotification(remoteMessage.data)
    }
    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d("islam", "sendRegistrationTokenToServer($token)")
    }
    private fun showNotification(remoteMessage: Map<String, String>) {
        val soundUri =
            Uri.parse("android.resource://" + applicationContext.packageName + "/" + R.raw.notification)

        val messageSenderId = remoteMessage["sender_id"]

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.action = Constants.OPEN_NOTIFICATION
        if (messageSenderId != null) {
            intent.putExtra(Constants.RECEIVER_ID, messageSenderId.toInt())
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent =
            PendingIntent.getActivity(
                applicationContext, 100, intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val pattern = longArrayOf(500, 500, 500, 500, 500, 500, 500, 500, 500)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val imageUrl = remoteMessage["imageUrl"]
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(remoteMessage["title"])
            .setContentText(remoteMessage["body"])
            .setContentIntent(pendingIntent)
            .setSound(soundUri)
            .setVibrate(pattern)

        var futureTarget = Glide.with(this)
            .asBitmap()
            .load(R.drawable.logo)
            .submit()
        if (imageUrl != null) {
            futureTarget = Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .submit()
        }
        val bitmap = futureTarget.get()
        notification.setLargeIcon(bitmap)
        Glide.with(this).clear(futureTarget)


        notificationManager.notify(Random().nextInt(), notification.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val soundUri =
            Uri.parse("android.resource://" + applicationContext.packageName + "/" + R.raw.notification)
        val channelName = "Snail Channel"
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM)
            .build()
        val channel = NotificationChannel(
            CHANNEL_ID, channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "A notification from Snail"
            enableLights(true)
            lightColor = Color.GREEN
        }
        channel.setSound(soundUri, audioAttributes)
        notificationManager.createNotificationChannel(channel)
    }

    private fun sendBroadCast() {
        val intent = Intent()
        intent.action = "com.tawajood.Snail.Notify"
        sendBroadcast(intent)
    }
}
