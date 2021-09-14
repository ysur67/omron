package com.example.omron.data.repository.implementation

import android.content.Context
import android.util.Log
import com.example.omron.data.repository.ConnectionRepository
import com.example.omron.utils.Const
import com.example.omron.utils.isEmpty
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Interface.*
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.ErrorInfo
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.OmronPeripheral
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.OmronUtility.OmronConstants
import io.reactivex.rxjava3.core.Observable
import java.lang.NullPointerException
import java.util.ArrayList
import javax.inject.Inject

class OmronConnectionRepository @Inject constructor(
    context: Context
    ) : OmronBaseRepository(context), ConnectionRepository {

    override fun createBond(device: OmronPeripheral) : Observable<OmronPeripheral> {
        return Observable.create{
            manager.connectPeripheral(device, true) {peripheral, error ->
                if (resultIsOk(peripheral, error)) {
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

    override fun resumeConnection(device: OmronPeripheral) : Observable<OmronPeripheral> {
        return Observable.create{
            manager
                .resumeConnectPeripheral(device, Const.CURRENT_DEVICE_NUMBER) { peripheral, error ->
                if (resultIsOk(peripheral, error)) {
                    it.onNext(peripheral)
                } else {
                    throw NullPointerException(error.messageInfo)
                }
            }
        }
    }

    override fun startDataTransfer(device: OmronPeripheral): Observable<OmronPeripheral> {
        return Observable.create{
            val listener =
                OmronPeripheralManagerDataTransferListener { peripheral, error ->
                    if (resultIsOk(peripheral, error)) {
                        it.onNext(peripheral)
                    } else {
                        throw NullPointerException(error.messageInfo)
                    }
                }
            manager.startDataTransferFromPeripheral(
                device,
                Const.CURRENT_DEVICE_NUMBER,
                true,
                OmronConstants.OMRONVitalDataTransferCategory.BloodPressure,
                listener
            )
        }
    }

    override fun endDataTransfer(device: OmronPeripheral) {
        manager.endDataTransferFromPeripheral() { peripheral, error ->
            if (resultIsOk(peripheral, error)) {
                Log.e("dasfdsa", "$peripheral")
            } else {
                Log.e("dsafdasfa", "$error")
            }
        }
    }

    override fun getRecordsData(device: OmronPeripheral): Observable<Any> {
        return Observable.create{
            val listener =
                OmronPeripheralListener { data, error ->
                    if (data != null) {
                        val bloodPressureItemList =
                            data[OmronConstants.OMRONVitalDataBloodPressureKey]
                        if (bloodPressureItemList != null) {
                            it.onNext(bloodPressureItemList)
                        }
                    } else {
                        it.onError(OutOfMemoryError(error?.messageInfo))
                    }
                }
            val result = device.getVitalData()
        }
    }
}