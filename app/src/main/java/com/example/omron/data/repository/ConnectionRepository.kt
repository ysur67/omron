package com.example.omron.data.repository

import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.OmronPeripheral
import io.reactivex.rxjava3.core.Observable

interface ConnectionRepository {
    fun createBond(device: OmronPeripheral)
    fun disconnect()
    fun resumeConnection(device: OmronPeripheral) : Observable<OmronPeripheral>
    fun startDataTransfer(device: OmronPeripheral) : Observable<OmronPeripheral>
    fun getRecordsData(device: OmronPeripheral) : Observable<HashMap<String, Any>>
}
