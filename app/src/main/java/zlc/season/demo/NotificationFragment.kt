package zlc.season.demo

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import zlc.season.demo.databinding.FragmentNotificationBinding
import kotlin.random.Random

class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val channelId = "test"
    private val channelName = "test channel "
    private val channelDescription = "test channel desc"
    private val notificationManager by lazy {
        requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                println("permission allowed")
            } else {
                println("permission rejected")
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRequestPermission.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 33) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        binding.btnSendNotification.setOnClickListener {
            createNotificationChannel()
            sendNotification("Test", "This is a test notification")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isEnableNotification(): Boolean {
        val notificationManagerCompat = NotificationManagerCompat.from(requireContext())
        return notificationManagerCompat.areNotificationsEnabled()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationChannel.description = channelDescription

            notificationChannel.enableVibration(false)
            notificationChannel.enableLights(false)
            notificationChannel.vibrationPattern = longArrayOf(0L)
            notificationChannel.setSound(null, null)

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun sendNotification(title: String, content: String) {
        val notificationBuilder = NotificationCompat.Builder(requireContext(), channelId)
        notificationBuilder.setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setAutoCancel(true)
            .setTicker("aaa")
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setDefaults(Notification.DEFAULT_ALL)
        val notification = notificationBuilder.build()
        notificationManager.notify(Random(100).nextInt(), notification)
    }
}