package com.example.omron.data.repository.implementation

import android.content.Context
import com.example.omron.data.repository.ConnectionRepository
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Interface.OmronPeripheralManagerConnectListener
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.ErrorInfo
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.OmronPeripheral
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.internal.operators.observable.ObservableAll
import java.lang.NullPointerException
import javax.inject.Inject

class OmronConnectionRepository @Inject constructor(
    context: Context
    ) : OmronBaseRepository(context), ConnectionRepository {

    override fun connect(device: OmronPeripheral): Observable<OmronPeripheral> {
        return Observable.create{
            manager.connectPeripheral(
                device
            ) { peripheral, error ->
                if (peripheral != null) {
                    it.onNext(peripheral)
                } else {
                    it.onError(NullPointerException(error.messageInfo))
                }
            }
        }
    }

    override fun disconnect() {
        manager.endConnectPeripheral(OmronPeripheralManagerConnectListener{ _, _ ->

        })
    }
}