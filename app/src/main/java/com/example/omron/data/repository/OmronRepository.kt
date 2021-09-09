package com.example.omron.data.repository

import android.content.Context
import android.util.Log
import com.example.omron.utils.Const
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.DeviceConfiguration.OmronPeripheralManagerConfig
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Interface.OmronPeripheralManagerScanListener
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.LibraryManager.OmronPeripheralManager
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.LibraryManager.SharedManager
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.ErrorInfo
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.OmronPeripheral
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.OmronUtility.OmronConstants
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import java.lang.NullPointerException
import javax.inject.Inject

class OmronRepository @Inject constructor(
    private val context: Context
    ) : ScanRepository {

    private var manager: SharedManager = OmronPeripheralManager.sharedManager(context)
    private val DEBUG_TAG = "OMRON_SCANNER_SERVICE"

    override fun init() {
        OmronPeripheralManagerConfig.timeoutInterval = Const.DISCOVER_TIMEOUT
        OmronPeripheralManagerConfig.userHashId = "test@gtes.com"

        val bloodPressureSettings = hashMapOf(
            OmronConstants.OMRONDevicePersonalSettings.BloodPressureTruReadEnableKey to
                    OmronConstants.OMRONDevicePersonalSettingsBloodPressureTruReadStatus.On,
            OmronConstants.OMRONDevicePersonalSettings.BloodPressureTruReadIntervalKey to
                    OmronConstants.OMRONDevicePersonalSettingsBloodPressureTruReadInterval.Interval30
        )
        val settings = hashMapOf(
            OmronConstants.OMRONDevicePersonalSettings.BloodPressureKey to bloodPressureSettings
        )
        val personalSettings = hashMapOf(OmronConstants.OMRONDevicePersonalSettingsKey to settings)
        OmronPeripheralManagerConfig.deviceSettings = arrayListOf(personalSettings)
        manager.startManager()
    }

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
}