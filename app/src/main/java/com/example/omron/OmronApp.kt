package com.example.omron

import android.app.Application
import com.example.omron.R
import com.example.omron.di.component.AppComponent
import com.example.omron.di.component.DaggerAppComponent
import com.example.omron.di.module.AppModule
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.LibraryManager.OmronPeripheralManager

class OmronApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        OmronPeripheralManager.sharedManager(this)
            .setAPIKey(getString(R.string.omron_api_key), null)
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}