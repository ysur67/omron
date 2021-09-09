package com.example.omron.di.component

import com.example.omron.di.module.AppModule
import com.example.omron.di.module.RepositoryModule
import com.example.omron.di.module.ViewModelModule
import com.example.omron.presentation.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, RepositoryModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}