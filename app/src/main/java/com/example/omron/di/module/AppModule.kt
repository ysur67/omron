package com.example.omron.di.module

import android.content.Context
import com.example.omron.presentation.activity.MainActivity
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context
}