package com.jetpack.application.index

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.jetpack.support.BaseViewModel
import io.reactivex.rxjava3.core.Observable

class IndexActivityViewModel(private val app:Application):BaseViewModel(app) {
    val tempData :MutableLiveData<ArrayList<String>> by lazy { MutableLiveData() }

}