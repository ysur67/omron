package com.example.omron.data.repository

import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.OmronPeripheral
import io.reactivex.rxjava3.core.Flowable


interface ScanRepository {
    fun startScan() : Flowable<ArrayList<OmronPeripheral>>
    fun stopScan()
}
