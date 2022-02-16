package com.sanchay.alarmeasy

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var pickTime : MaterialTimePicker
    private lateinit var getCalender : Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar  = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setTitle("Alarm Easy")
        toolbar.setLogo(R.drawable.alarmset)
        setSupportActionBar(toolbar)
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val name : CharSequence = "sanchayChannel"
            val description = "Alarm Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("sanchay", name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
        val chooseTime : MaterialButton = findViewById(R.id.choose_time)
        val setAlarm : MaterialButton = findViewById(R.id.set_alarm)
        val cancelAlarm : MaterialButton = findViewById(R.id.cancel_alarm)
        val timeShow : TextView = findViewById(R.id.timeShow)
        val alarmtToggle : LinearLayout = findViewById(R.id.alarm_toggle)
        cancelAlarm.visibility = View.GONE
        setAlarm.visibility = View.GONE
        chooseTime.setOnClickListener{
            pickTime = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Choose Your Alarm Time")
                .build()
            pickTime.show(supportFragmentManager, "sanchay")
            pickTime.addOnPositiveButtonClickListener {
                if(pickTime.hour > 12){
                    timeShow.text = String.format("%02d", pickTime.hour-12) + " : " + String.format("%02d", pickTime.minute) + "PM"
                }else{
                    timeShow.text = String.format("%02d", pickTime.hour) + " : " + String.format("%02d", pickTime.minute) + "AM"
                }
                getCalender = Calendar.getInstance()
                getCalender[Calendar.HOUR_OF_DAY] = pickTime.hour
                getCalender[Calendar.MINUTE] = pickTime.minute
                getCalender[Calendar.SECOND] = 0
                getCalender[Calendar.MILLISECOND] = 0
                chooseTime.visibility = View.GONE
                setAlarm.visibility = View.VISIBLE

            }
            pickTime.addOnNegativeButtonClickListener {  }
        }

        setAlarm.setOnClickListener {
            alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, Receiver::class.java)
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,getCalender.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent
            )
            setAlarm.visibility = View.GONE
            cancelAlarm.visibility = View.VISIBLE
            Toast.makeText(this, "Alarm Setup", Toast.LENGTH_LONG).show()
        }

        cancelAlarm.setOnClickListener {
            alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, Receiver::class.java)
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
            alarmManager.cancel(pendingIntent)
            Toast.makeText(this, "Alarm Cancel", Toast.LENGTH_LONG).show()
            chooseTime.visibility = View.VISIBLE
            cancelAlarm.visibility = View.GONE
            timeShow.text =  " 0:00 " + "AM"
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item2->{
                finish()
                return true
            }
            R.id.item1->{
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT,"Download My app")
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent,"share"))
                return true
            }
        }
        return false

    }

}