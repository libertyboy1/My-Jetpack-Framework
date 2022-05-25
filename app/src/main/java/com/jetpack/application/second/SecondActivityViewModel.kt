package com.jetpack.application.second

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jetpack.support.BaseViewModel
import io.reactivex.rxjava3.core.Observable

class SecondActivityViewModel(private val app:Application):BaseViewModel(app) {
    val tempData :MutableLiveData<ArrayList<String>> by lazy { MutableLiveData() }
}