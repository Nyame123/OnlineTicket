package com.bismark.onlineticket.app

import android.app.Application
import com.bismark.onlineticket.di.AppComponent
import com.bismark.onlineticket.di.DaggerAppComponent
import com.bismark.onlineticket.di.DatabaseModule

class OnlineTicketApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .databaseModule(DatabaseModule(this))
            .build()
    }
}