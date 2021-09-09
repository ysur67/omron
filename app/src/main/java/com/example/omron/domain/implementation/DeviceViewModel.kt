package com.example.omron.domain.implementation


import com.example.omron.data.repository.ScanRepository
import com.example.omron.domain.BaseViewModel
import javax.inject.Inject

class DeviceViewModel @Inject constructor(private val repository: ScanRepository) : BaseViewModel() {
    fun startScan() {
        repository.startScan()
    }
}
