package com.example.omron.di.module

import android.content.Context
import com.example.omron.data.repository.ConnectionRepository
import com.example.omron.data.repository.implementation.OmronScanRepository
import com.example.omron.data.repository.ScanRepository
import com.example.omron.data.repository.implementation.OmronConnectionRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideScanRepository(context: Context) : ScanRepository {
        return OmronScanRepository(context)
    }

    @Provides
    @Singleton
    fun provideConnectionRepository(context: Context) : ConnectionRepository {
        return OmronConnectionRepository(context)
    }
}
