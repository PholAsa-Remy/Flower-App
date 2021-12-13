package com.example.project

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var alarmManager: AlarmManager
    lateinit var calendar: Calendar
    lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView( binding.root)

        binding.recallbutton.setOnClickListener(){
            var goToRecall : Intent = Intent (this, RecallActivity:: class.java)
            startActivity(goToRecall)
        }

        binding.recherchebutton.setOnClickListener(){
            var goToResearch : Intent = Intent (this, ResearchActivity:: class.java)
            startActivity(goToResearch)
        }

        createNotificationChannel()
        setAlarm()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "notificationChannel"
            val descriptionText = "next watering alarm notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("alarmRecall", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    //set the alarm
    private fun setAlarm (){
        calendar =  Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 45);
        calendar.set(Calendar.SECOND, 0)

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent (this, AlarmRecall :: class.java)
        pendingIntent =PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )
    }
}