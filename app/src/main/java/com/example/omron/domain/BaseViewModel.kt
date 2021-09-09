package com.example.omron.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    protected var loading = null
        set(value) = _isLoading.postValue(value)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

}