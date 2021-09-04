package com.xyz.app

import android.app.Application
import android.content.Context

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        sApplication = this
    }

    companion object {
        private var sApplication: Application? = null

        fun getContext(): Context? {
            return sApplication?.applicationContext
        }
    }
}