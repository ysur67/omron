package com.example.omron.di.component

import com.example.omron.di.module.AppModule
import com.example.omron.di.module.RepositoryModule
import com.example.omron.di.module.ViewModelModule
import com.example.omron.presentation.activity.MainActivity
import com.example.omron.presentation.fragment.ControlDeviceFragment
import com.example.omron.presentation.fragment.ScannedDevicesFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, RepositoryModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(scannedDevicesFragment: ScannedDevicesFragment)
    fun inject(controlDeviceFragment: ControlDeviceFragment)
}