package com.example.artisana.core.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.artisana.MainActivity
import com.example.artisana.R

class NotificationService(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private fun createClickIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    @SuppressLint("MissingPermission")
    fun showCartLimitNotification() {
        createNotificationChannel("cart_limit_channel", "Limite du Panier", "Notifications pour la limite du panier")
        val appIcon = BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher)
        val pendingIntent = createClickIntent()

        val notification = NotificationCompat.Builder(context, "cart_limit_channel")
            .setLargeIcon(appIcon)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText("Reviser votre achat de 3000dh")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(1, notification)
    }

    @SuppressLint("MissingPermission")
    fun showProductOutOfStockNotification(productName: String) {
        createNotificationChannel("out_of_stock_channel", "Produit en rupture de stock", "Notifications pour les produits en rupture de stock")
        val pendingIntent = createClickIntent()

        val notification = NotificationCompat.Builder(context, "out_of_stock_channel")
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText("Le produit '$productName' est malheureusement en rupture de stock.")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(2, notification)
    }

    private fun createNotificationChannel(id: String, name: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH).apply {
                this.description = description
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}