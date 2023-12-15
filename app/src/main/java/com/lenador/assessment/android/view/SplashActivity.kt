package com.lenador.assessment.android.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.lenador.assessment.android.R
import com.lenador.assessment.android.view.dashboard.Dashboard


/**
 * Custom Splash Screen to give a nice user experience to the user that the app is launching.
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    /**
     * Local variables
     */
    private val delayMillis: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        /**
         * Access main thread and navigate to dashboard after set amount of delay.
         */
        Handler(Looper.getMainLooper()).postDelayed({
            val dashboardIntent = Intent(this, Dashboard :: class.java)
            startActivity(dashboardIntent)
        }, delayMillis)
    }
}