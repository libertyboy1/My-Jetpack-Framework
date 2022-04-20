package com.jetpack.application.index.fragment

import androidx.lifecycle.Lifecycle
import com.jetpack.support.presenter.BasePresenter

class IndexFragmentPresenter(private val lifecycle: Lifecycle, private val mFragment:IndexFragment) : BasePresenter(lifecycle){
    override fun doEvent() {
        mFragment.setRequest(true,{},{})
    }
}

