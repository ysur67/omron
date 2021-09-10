package com.example.omron.data.repository.implementation

import android.content.Context
import com.example.omron.data.repository.ScanRepository
import com.example.omron.utils.Const
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.DeviceConfiguration.OmronPeripheralManagerConfig
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Interface.OmronPeripheralManagerScanListener
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.LibraryManager.OmronPeripheralManager
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.LibraryManager.SharedManager
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.OmronPeripheral
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.OmronUtility.OmronConstants
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import java.lang.NullPointerException
import javax.inject.Inject

class OmronScanRepository @Inject constructor(
    context: Context
    ) : OmronBaseRepository(context), ScanRepository {
    private val DEBUG_TAG = "OMRON_SCANNER_SERVICE"

    override fun startScan() : Flowable<ArrayList<OmronPeripheral>> {
        return Flowable.create({
            val listener =
                OmronPeripheralManagerScanListener { scannedPeripherals, error ->
                    if (scannedPeripherals != null) {
                        it.onNext(scannedPeripherals)
                    } else {
                        it.onError(NullPointerException(error.messageInfo))
                    }
                }
            manager.startScanPeripherals(listener)
        }, BackpressureStrategy.LATEST)
    }

    override fun stopScan() {
        manager.stopScanPeripherals()
    }

    override fun connect(device: OmronPeripheral) {

    }
}