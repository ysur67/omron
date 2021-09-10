package com.example.omron.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.omron.di.ViewModelFactory
import com.example.omron.di.ViewModelKey
import com.example.omron.domain.implementation.ConnectionViewModel
import com.example.omron.domain.implementation.ScanViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ScanViewModel::class)
    abstract fun bindScanViewModel(viewModel: ScanViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConnectionViewModel::class)
    abstract fun bindConnectionViewModel(viewModel: ConnectionViewModel) : ViewModel
}
