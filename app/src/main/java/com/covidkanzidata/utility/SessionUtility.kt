package com.covidkanzidata.utility

import android.content.Context
import com.covidkanzidata.AppController


object SessionObject {

    var authToken: String? = ""

    fun putAccessToken(iAccessToken: String?) {
        authToken = "${Constants.BEARER} $iAccessToken"
    }

    fun setToken(key: String, token: String) {
        val sharedPref =
            AppController.instance.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        with(sharedPref!!.edit()) {
            putString(key, token)
            commit()
        }
    }

    fun getToken(key: String): String? {
        val sharedPref =
            AppController.instance.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        return sharedPref.getString(key, null)
    }
}