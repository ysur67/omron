package com.example.omron.data.repository

import io.reactivex.Flowable

interface ScanRepository {
    fun init()
    fun startScan()
    fun stopScan()
}
