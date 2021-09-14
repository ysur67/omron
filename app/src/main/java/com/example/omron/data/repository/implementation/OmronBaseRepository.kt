package com.example.omron.data.repository.implementation

import android.content.Context
import com.example.omron.utils.Const
import com.example.omron.utils.isEmpty
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.DeviceConfiguration.OmronPeripheralManagerConfig
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.LibraryManager.OmronPeripheralManager
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.LibraryManager.SharedManager
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.ErrorInfo
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.OmronPeripheral
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.OmronUtility.OmronConstants

open class OmronBaseRepository(protected val context: Context) {

    protected val manager: SharedManager = OmronPeripheralManager.sharedManager(context)
    init {
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

    protected fun resultIsOk(device: OmronPeripheral?, error: ErrorInfo) : Boolean {
        return device != null && error.isEmpty
    }
}