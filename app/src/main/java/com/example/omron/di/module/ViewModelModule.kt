package com.example.omron.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.omron.di.ViewModelFactory
import com.example.omron.di.ViewModelKey
import com.example.omron.domain.implementation.DeviceViewModel
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
    @ViewModelKey(DeviceViewModel::class)
    abstract fun bindDeviceViewModel(viewModel: DeviceViewModel) : ViewModel
}
