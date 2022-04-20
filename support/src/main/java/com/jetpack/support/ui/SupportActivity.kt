package com.jetpack.support.ui

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.google.android.material.appbar.MaterialToolbar
import com.jetpack.support.BaseViewModel
import com.jetpack.support.api.sendRequest
import com.jetpack.support.presenter.BasePresenter
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

abstract class SupportActivity<B : ViewDataBinding, P : BasePresenter, V : BaseViewModel>():
    RxAppCompatActivity() {

    fun getRetrofit() = BaseApplication.instance.getRetrofit()
    abstract fun getPresenterInstance(): P
    abstract val isHaveToolbar: Boolean
    val mPresenter: P by lazy { getPresenterInstance() }
    abstract val vm: V
    abstract val binding: B
    private val missingPermissionList = arrayListOf<String>()
    private val secondMissingPermissionList = arrayListOf<String>()
    lateinit var toolBarView: MaterialToolbar
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        secondMissingPermissionList.clear()
        it.forEach {
            if (!it.value) {
                secondMissingPermissionList.add(it.key)
            }
        }
        if (secondMissingPermissionList.isNotEmpty()) {
            requestPermission(secondMissingPermissionList, false)
        } else {
            permissionIsGranted(true)
        }
    }

    /**
     * 申请权限
     */
    fun requestPermission(permissions: ArrayList<String>) {
        requestPermission(permissions, true)
    }

    private fun requestPermission(permissions: ArrayList<String>, isFirst: Boolean) {
        missingPermissionList.clear()
        permissions.forEach {
            if (isFirst && ContextCompat.checkSelfPermission(
                    this,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                missingPermissionList.add(it)
            } else if (!isFirst && shouldShowRequestPermissionRationale(it)) {
                missingPermissionList.add(it)
            } else {
                if (!isFirst) {
                    permissionIsGranted(false)
                    return
                }
            }
        }

        if (missingPermissionList.isNotEmpty()) {
            requestPermissionLauncher.launch(missingPermissionList.toArray(arrayOf()))
        }

    }

    /**
     * 权限申请结果回调
     * isGranted：true权限通过，false权限拒绝
     */
    abstract fun permissionIsGranted(isGranted: Boolean)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(mPresenter)
        binding.lifecycleOwner = this
        if (isHaveToolbar) {
            toolBarView = binding.root.findViewById(com.jetpack.support.R.id.materialToolbar)
            setSupportActionBar(toolBarView)
            toolBarView.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    fun setRequest(
        isWhole: Boolean,
        onSuccess: (it: Any) -> Unit,
        onComplete: () -> Unit
    ) {
        if (getRequestList().isNullOrEmpty()){
            throw Exception("网络请求接口集合\"getRequestList()\"不能为空")
        }else{
            sendRequest(isWhole, {mPresenter.currentRequestDisposable = it}, onSuccess,  onComplete, vm.requests.apply { value = getRequestList() })
        }

    }

    /**
     * 网络接口请求集合
     * 请求顺序遵循先入先出原则
     */
    abstract fun getRequestList():ArrayList<Observable<*>?>?

}