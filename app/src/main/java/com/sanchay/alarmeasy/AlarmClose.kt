package com.sanchay.alarmeasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class AlarmClose : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_close)
        val toolbar  = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setTitle("Alarm Easy")
        toolbar.setLogo(R.drawable.alarmset)
        setSupportActionBar(toolbar)
        val simpleRatingBar = findViewById<RatingBar>(R.id.simpleRatingBar)
        val submitButton = findViewById<MaterialButton>(R.id.submitButton)
        val go_back = findViewById<TextView>(R.id.go_back)
        submitButton.setOnClickListener {
            val totalStars = "Total Stars: " + simpleRatingBar.numStars
            val rating = "Rating: " + simpleRatingBar.rating
            Toast.makeText(this, """ Thanks For Giving $rating """.trimIndent(),
                Toast.LENGTH_LONG).show()
        }
        go_back.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
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