package com.jetpack.application.model

import androidx.lifecycle.MutableLiveData
import java.io.Serializable

class ResponseLiveData {
    companion object{
        var mAppStatusEntity = MutableLiveData<AppStatusEntity>()
    }
}

data class AppStatusEntity(
    var data: AppStatusDataEntity? = null
) :  Serializable

data class AppStatusDataEntity(
    var data: String? = null
) : Serializable