package com.jetpack.application.index

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.jetpack.support.presenter.BasePresenter

class IndexActivityPresenter(private val lifecycle: Lifecycle, private val mActivity:IndexActivity) : BasePresenter(lifecycle){
    override fun doEvent() {
        mActivity.setRequest(false,{},{})
    }



}

