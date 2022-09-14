package zlc.season.demo

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat


class ForegroundService : Service() {

    private val channelId = "test"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification()
        startForeground(1, notification)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        stopForeground(true)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotification(
        title: String = "Foreground",
        content: String = "This is a foreground Service"
    ): Notification {
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        notificationBuilder.setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setAutoCancel(true)
            .setTicker("aaa")
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setDefaults(Notification.DEFAULT_ALL)
        return notificationBuilder.build()
    }
}