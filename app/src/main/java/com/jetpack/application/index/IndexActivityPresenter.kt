package com.jetpack.application.index

import android.util.Log
import androidx.lifecycle.Lifecycle
import com.jetpack.application.model.ResponseLiveData
import com.jetpack.support.presenter.BasePresenter

class IndexActivityPresenter(
    private val lifecycle: Lifecycle,
    private val mActivity: IndexActivity
) : BasePresenter(lifecycle) {

    override fun onRequestComplete() {
    }

    override fun onRequestSuccess(it: Any) {

    }

    override fun doEvent() {

        ResponseLiveData.mAppStatusEntity.observe(mActivity){
            Log.e("suaobo","$it")
        }

    }

}

