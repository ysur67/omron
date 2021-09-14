package com.example.omron.utils

import com.omronhealthcare.OmronConnectivityLibrary.OmronLibrary.Model.ErrorInfo

val ErrorInfo.isEmpty: Boolean
    get() = resultCode == 0
