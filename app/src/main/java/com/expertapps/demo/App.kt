package com.expertapps.demo

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.ktx.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    override fun attachBaseContext(base: Context) {
        App.instance = this
        super.attachBaseContext(base)
    }


    companion object {
        lateinit var instance: App
    }

}