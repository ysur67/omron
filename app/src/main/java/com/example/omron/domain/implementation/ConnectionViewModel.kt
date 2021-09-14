package com.example.omron.domain.implementation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.omron.data.repository.ConnectionRepository
import com.example.omron.domain.BaseViewModel
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.OmronPeripheral
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.NullPointerException
import java.util.logging.Handler
import javax.inject.Inject

class ConnectionViewModel @Inject constructor(
    private val repository: ConnectionRepository
    ) : BaseViewModel() {
    private val _currentDevice = MutableLiveData<OmronPeripheral>(null)
    val currentDevice: LiveData<OmronPeripheral>
        get() = _currentDevice

    private var _selectedDevice = MutableLiveData<OmronPeripheral>(null)
    val selectedDevice: LiveData<OmronPeripheral>
        get() = _selectedDevice

    fun selectDevice(device: OmronPeripheral) {
        _selectedDevice.postValue(device)
    }

    fun createBond() {
        val currentDevice = requireDevice()
        loading = true
        repository.createBond(currentDevice)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnError{
                throw it
            }
            .subscribe{
                onDeviceDidBond(it)
            }
    }

    fun disconnect() {
        repository.disconnect()
    }

    private fun onDeviceDidBond(device: OmronPeripheral) {
        repository.resumeConnection(device)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnError{
                throw it
            }
            .subscribe {
                startDataTransfer(it)
            }
    }

    fun requestRecordsData() {
        val currentDevice = requireDevice()
        repository.getRecordsData(currentDevice)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .onErrorComplete{
                throw it
            }
            .subscribe{
                Log.e("DEVICE", "$it")
            }

    }

    private fun requireDevice() : OmronPeripheral {
        return _selectedDevice.value ?: throw NullPointerException("Устройство не было выбрано")
    }

    private fun startDataTransfer(device: OmronPeripheral) {
        repository.startDataTransfer(device)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnError {
                throw it
            }
            .doOnNext{
                _currentDevice.postValue(it)
            }
    }
}
