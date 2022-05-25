package com.jetpack.support.presenter

import androidx.lifecycle.Lifecycle
import com.jetpack.support.observer.BaseLifecycleObserver
import io.reactivex.rxjava3.disposables.Disposable

abstract class BasePresenter(private val lifecycle: Lifecycle) : BaseLifecycleObserver(lifecycle) {
    var currentRequestDisposable: Disposable?=null
    abstract fun onRequestComplete()
    abstract fun onRequestSuccess(it:Any)
}