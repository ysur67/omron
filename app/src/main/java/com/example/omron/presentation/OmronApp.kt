package com.example.omron.presentation

import android.app.Application
import com.example.omron.R
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.LibraryManager.OmronPeripheralManager

class OmronApp : Application() {
    override fun onCreate() {
        super.onCreate()
        OmronPeripheralManager.sharedManager(this)
            .setAPIKey(getString(R.string.omron_api_key), null)
    }
}