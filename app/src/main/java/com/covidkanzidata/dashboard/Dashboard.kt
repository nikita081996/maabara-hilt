package com.covidkanzidata.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.covidkanzidata.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)
    }
}
