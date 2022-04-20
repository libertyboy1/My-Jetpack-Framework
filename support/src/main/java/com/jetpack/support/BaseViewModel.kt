package com.jetpack.support

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Observable

abstract class BaseViewModel(private val app: Application) : AndroidViewModel(app) {

    val requests: MutableLiveData<ArrayList<Observable<*>?>> by lazy {
        MutableLiveData()
    }

}