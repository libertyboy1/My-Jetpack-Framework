package com.jetpack.application.second

import android.util.Log
import androidx.lifecycle.Lifecycle
import com.jetpack.application.model.ResponseLiveData
import com.jetpack.support.presenter.BasePresenter
import java.lang.reflect.Modifier

class SecondActivityPresenter(
    private val lifecycle: Lifecycle,
    private val mActivity: SecondActivity
) : BasePresenter(lifecycle) {

    override fun onRequestComplete() {
    }

    override fun onRequestSuccess(it: Any) {

    }

    override fun doEvent() {

        ResponseLiveData.mAppStatusEntity.observe(mActivity){
            Log.e("suaobo","SecondActivityPresenter:$it")
        }

    }

}

