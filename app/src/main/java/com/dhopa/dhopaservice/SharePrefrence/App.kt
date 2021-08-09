package com.beaute.user.SharePrefrence

import android.app.Application
import android.content.Context
import com.dhopa.dhopaservice.SharePrefrence.AppPreferences

class App : Application() {
    lateinit var context: Context

    companion object {
        lateinit var appPreference1: AppPreferences
        val appInstance: App? = null

        fun getAppPreference(): AppPreferences? {
            return appPreference1
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        appPreference1 = AppPreferences.init(context, "DhopaUser")
    }
}