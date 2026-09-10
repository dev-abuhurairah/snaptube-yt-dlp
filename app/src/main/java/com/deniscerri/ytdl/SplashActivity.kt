package com.deniscerri.ytdl

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.animation.AccelerateDecelerateInterpolator
import com.deniscerri.ytdl.receiver.ShareActivity
import com.deniscerri.ytdl.ui.BaseActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Fullscreen layout
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            )
        }

        // Animate central brand elements
        val brandContainer = findViewById<View>(R.id.vidsnap_splash_brand_container)
        brandContainer?.apply {
            alpha = 0f
            scaleX = 0.82f
            scaleY = 0.82f
            animate()
                .alpha(1f)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(900)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        }

        // Transition to target activity after short delay
        Handler(Looper.getMainLooper()).postDelayed({
            val incomingIntent = intent
            val action = incomingIntent?.action
            if (Intent.ACTION_SEND == action || Intent.ACTION_VIEW == action) {
                // Forward share intent to ShareActivity
                incomingIntent.setClass(this, ShareActivity::class.java)
                startActivity(incomingIntent)
            } else {
                // Open MainActivity
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 1300)
    }
}
