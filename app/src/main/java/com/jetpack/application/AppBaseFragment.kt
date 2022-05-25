package com.jetpack.application

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.jetpack.support.BaseViewModel
import com.jetpack.support.presenter.BasePresenter
import com.jetpack.support.ui.SupportFragment

abstract class AppBaseFragment<A : AppCompatActivity, P : BasePresenter, V : BaseViewModel, B : ViewDataBinding> :
    SupportFragment<A, P, V, B>() {
}