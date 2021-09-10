package com.example.omron.domain.implementation

import com.example.omron.data.repository.ConnectionRepository
import com.example.omron.domain.BaseViewModel
import javax.inject.Inject

class ConnectionViewModel @Inject constructor(private val repository: ConnectionRepository) : BaseViewModel() {

}
