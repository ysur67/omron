package com.example.omron.data.repository.implementation

import android.content.Context
import com.example.omron.data.repository.ConnectionRepository
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.OmronPeripheral
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class OmronConnectionRepository @Inject constructor(
    context: Context
    ) : OmronBaseRepository(context), ConnectionRepository {

    override fun connect(): Observable<OmronPeripheral> {
        TODO("Not yet implemented")
    }

    override fun disconnect() {
        TODO("Not yet implemented")
    }
}