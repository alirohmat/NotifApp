package com.NotifApp

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    companion object {
        private const val NOTIF_ID = 1
        private const val PERMISSION_REQUEST_CODE = 100
    }

    private lateinit var tokenText: TextView
    private lateinit var sendNotifButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tokenText = findViewById(R.id.tokenText)
        sendNotifButton = findViewById(R.id.sendNotifButton)

        NotificationUtils.createNotificationChannel(this)
        requestNotificationPermission()
        getFCMToken()

        sendNotifButton.setOnClickListener {
            sendLocalNotification()
        }

        tokenText.setOnClickListener {
            copyTokenToClipboard()
        }
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                tokenText.text = "Token FCM:\n$token"
            }
        }
    }

    private fun copyTokenToClipboard() {
        val tokenContent = tokenText.text.toString()
        // Hanya copy jika token sudah tersedia (bukan placeholder awal)
        if (!tokenContent.startsWith("Token FCM:\n")) return
        val token = tokenContent.removePrefix("Token FCM:\n")
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("FCM Token", token))
        Toast.makeText(this, "Token copied!", Toast.LENGTH_SHORT).show()
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun sendLocalNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }

        val builder = NotificationCompat.Builder(this, NotificationUtils.CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("NotifApp")
            .setContentText("Halo! Ini notifikasi dari NotifApp!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        NotificationManagerCompat.from(this).notify(NOTIF_ID, builder.build())
    }
}
