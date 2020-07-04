package com.covidkanzidata

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.covidkanzidata.dashboard.Dashboard
import com.covidkanzidata.utility.Constants
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            delay(Constants.SPLASH_DELAY)
            gotoDashboard()
        }
    }

    private fun gotoDashboard() {
        val intent = Intent(this, Dashboard::class.java)
        startActivity(intent)
        finish()
    }
}
