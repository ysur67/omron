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
import java.lang.NullPointerException
import javax.inject.Inject

class OmronRepository @Inject constructor(
    private val context: Context
    ) : ScanRepository {

    private var manager: SharedManager
    init {
        manager = OmronPeripheralManager.sharedManager(context)
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

    private val DEBUG_TAG = "OMRON_SCANNER_SERVICE"

    override fun init() {
        manager = OmronPeripheralManager.sharedManager(context)
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

    override fun startScan() {
        val listener =
            OmronPeripheralManagerScanListener { scannedPeripherals, error ->
                if (scannedPeripherals != null) {
                    for (peripheral in scannedPeripherals) {
                        Log.e(DEBUG_TAG, "FOUND NEW DEVICE ${peripheral.modelName}")
                    }
                } else {
                    throw NullPointerException("sdf")
                }
            }
        manager.startScanPeripherals(listener)
    }

    override fun stopScan() {
        TODO("Not yet implemented")
    }

}