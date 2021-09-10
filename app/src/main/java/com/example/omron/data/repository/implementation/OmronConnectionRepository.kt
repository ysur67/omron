package com.example.omron.data.repository.implementation

import android.content.Context
import android.util.Log
import com.example.omron.data.repository.ConnectionRepository
import com.example.omron.utils.Const
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Interface.OmronPeripheralManagerConnectListener
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.OmronPeripheral
import io.reactivex.rxjava3.core.Observable
import java.lang.NullPointerException
import javax.inject.Inject

class OmronConnectionRepository @Inject constructor(
    context: Context
    ) : OmronBaseRepository(context), ConnectionRepository {

    override fun createBond(device: OmronPeripheral) {
        manager.connectPeripheral(device) {_, _ ->

        }
    }

    override fun disconnect() {
        manager.endConnectPeripheral(OmronPeripheralManagerConnectListener{ _, _ ->

        })
    }

    override fun resumeConnection(device: OmronPeripheral) : Observable<OmronPeripheral> {
        return Observable.create{
            manager
                .resumeConnectPeripheral(device, Const.CURRENT_DEVICE_NUMBER) { peripheral, error ->
                if (peripheral != null) {
                    it.onNext(peripheral)
                } else {
                    throw NullPointerException(error.messageInfo)
                }
            }
        }
    }
}