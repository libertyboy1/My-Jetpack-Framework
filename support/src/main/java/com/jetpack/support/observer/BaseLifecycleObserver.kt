package com.jetpack.support.observer

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

abstract class BaseLifecycleObserver(private val lifecycle: Lifecycle) : DefaultLifecycleObserver {
    var enabled = false

    fun enable() {
        enabled = true
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            doEvent()
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        if (enabled)
            doEvent()
    }

    abstract fun doEvent()

}