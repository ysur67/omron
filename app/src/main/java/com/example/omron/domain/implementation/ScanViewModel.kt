package com.example.omron.domain.implementation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.omron.data.repository.ScanRepository
import com.example.omron.domain.BaseViewModel
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.OmronPeripheral
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ScanViewModel @Inject constructor(
    private val repository: ScanRepository
    ) : BaseViewModel() {

    private val _scannedDevices = MutableLiveData<ArrayList<OmronPeripheral>>(null)
    val scannedDevices: LiveData<ArrayList<OmronPeripheral>>
        get() = _scannedDevices

    private val _scanning = MutableLiveData(false)
    val isScanning: LiveData<Boolean>
        get() = _scanning

    fun toggleScan() {
        val currentState = _scanning.value ?: false
        if (currentState) stopScan() else startScan()
    }

    fun startScan() {
        _scanning.postValue(true)
        repository.startScan()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorComplete{
                throw it
            }
            .subscribe{
                _scannedDevices.postValue(it)
            }
    }

    fun stopScan() {
        _scanning.postValue(false)
        repository.stopScan()
        _scannedDevices.postValue(ArrayList())
    }
}
