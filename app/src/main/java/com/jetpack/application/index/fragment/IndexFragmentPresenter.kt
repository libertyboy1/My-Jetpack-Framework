package com.jetpack.application.index.fragment

import android.util.Log
import androidx.lifecycle.Lifecycle
import com.jetpack.application.model.ResponseLiveData
import com.jetpack.support.presenter.BasePresenter

class IndexFragmentPresenter(private val lifecycle: Lifecycle, private val mFragment:IndexFragment) : BasePresenter(lifecycle){

    override fun onRequestComplete() {
    }

    override fun onRequestSuccess(it: Any) {

    }

    override fun doEvent() {
        ResponseLiveData.mAppStatusEntity.observe(mFragment.viewLifecycleOwner){
            Log.e("suaobo","IndexFragmentPresenter:$it")
        }
    }
}

