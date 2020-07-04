package com.covidkanzidata

import android.app.Application
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Base class of Android app containing components like Activities and Services
 * Instantiated before all the activities or any other application objects have been created in Android app
 */
@HiltAndroidApp
class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)

        Timber.plant(Timber.DebugTree())
    }


    companion object {
        lateinit var instance: AppController
            private set
    }


}