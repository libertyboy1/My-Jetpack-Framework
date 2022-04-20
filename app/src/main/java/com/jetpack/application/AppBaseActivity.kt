package com.jetpack.application

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.MaterialToolbar
import com.jetpack.application.databinding.ActivityMainBinding
import com.jetpack.support.BaseViewModel
import com.jetpack.support.api.sendRequest
import com.jetpack.support.presenter.BasePresenter
import com.jetpack.support.ui.BaseApplication
import com.jetpack.support.ui.SupportActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

abstract class AppBaseActivity<B : ViewDataBinding, P : BasePresenter, V : BaseViewModel>() : SupportActivity<B, P, V>()