package com.sanchay.alarmeasy

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.airbnb.lottie.LottieAnimationView

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val appLogo: LottieAnimationView = findViewById(R.id.splash_img)
        appLogo.alpha = 0f

        val progessBar : ProgressBar = findViewById(R.id.progressBar)
        progessBar.max = 1000
        val progressval = 1000
        ObjectAnimator.ofInt(progessBar, "progress", progressval)
            .setDuration(5500).start()

        appLogo.animate().setDuration(6000).alpha(1f).withEndAction{
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}