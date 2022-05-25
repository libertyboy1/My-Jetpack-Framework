package com.jetpack.support.observer

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

abstract class BaseLifecycleObserver(private val lifecycle: Lifecycle) : DefaultLifecycleObserver {
//    var enabled = false
//    var isPause = false

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        doEvent()
    }

//    fun enable() {
//        enabled = true
//        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
//            doEvent()
//        }
//    }
//
//    override fun onStart(owner: LifecycleOwner) {
//        super.onStart(owner)
//        if (enabled && !isPause)
//            doEvent()
//    }
//
//    override fun onPause(owner: LifecycleOwner) {
//        super.onPause(owner)
//        isPause = true
//    }

    abstract fun doEvent()

}