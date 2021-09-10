package com.example.omron.data.repository

import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.OmronPeripheral
import io.reactivex.rxjava3.core.Observable

interface ConnectionRepository {
    fun connect() : Observable<OmronPeripheral>
    fun disconnect()
}
