package com.example.omron.domain.implementation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.omron.data.repository.ConnectionRepository
import com.example.omron.domain.BaseViewModel
import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.OmronPeripheral
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ConnectionViewModel @Inject constructor(
    private val repository: ConnectionRepository
    ) : BaseViewModel() {
    private val _currentDevice = MutableLiveData<OmronPeripheral>(null)
    val currentDevice: LiveData<OmronPeripheral>
        get() = _currentDevice

    fun createBond(device: OmronPeripheral) {
        repository.createBond(device)
    }

    fun disconnect() {
        repository.disconnect()
    }

    private fun resumeConnection(device: OmronPeripheral) {
        repository.resumeConnection(device)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorComplete{
                throw it
            }
            .subscribe{
                _currentDevice.postValue(it)
            }
    }
}
