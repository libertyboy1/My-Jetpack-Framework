package com.jetpack.application

import androidx.databinding.ViewDataBinding
import com.jetpack.support.BaseViewModel
import com.jetpack.support.presenter.BasePresenter
import com.jetpack.support.ui.SupportActivity

abstract class AppBaseActivity<B : ViewDataBinding, P : BasePresenter, V : BaseViewModel>() : SupportActivity<B, P, V>(){
}