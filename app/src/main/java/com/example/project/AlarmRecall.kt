package com.example.project

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.SimpleDateFormat
import java.util.*

class AlarmRecall : BroadcastReceiver() {

    //execute the function when the alarm is triggered
    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context?, intent: Intent?) {
        // Go to recall activity
        val newIntent = Intent(context, RecallActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        // Intent when click on notification
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_MUTABLE)

        // Search number of flower to water
        val nbFlower = getNumberFlower (context!!)

        if (nbFlower > 0) {
            // Create notification
            val builder = NotificationCompat.Builder(context, "alarmRecall")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Save your flower !!!")
                .setContentText("You have $nbFlower flower to water today !")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val notification = NotificationManagerCompat.from(context)
            notification.notify(1, builder.build())

        }
    }

    private fun getNumberFlower (context: Context) : Int {
        val flowerDb = FlowerBD.getDatabase(context)
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE).format(Date())
        var list : List<Flower> = listOf()
        val t = Thread {
            list = flowerDb.daoFlower().loadNextWateringFlower(date).toList()
        }
        t.start()
        t.join()
        return list.size
    }

}