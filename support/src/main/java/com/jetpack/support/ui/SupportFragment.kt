package com.jetpack.support.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jetpack.support.BaseViewModel
import com.jetpack.support.api.sendRequest
import com.jetpack.support.presenter.BasePresenter
import com.trello.rxlifecycle4.components.support.RxFragment
import io.reactivex.rxjava3.core.Observable

abstract class SupportFragment<A:AppCompatActivity,P: BasePresenter,V : BaseViewModel,B : ViewDataBinding>: RxFragment() {
    fun getParentActivity(): A = requireActivity() as A
    abstract fun getPresenterInstance(): P
    val mPresenter: P by lazy { getPresenterInstance() }
    abstract val vm: V
    lateinit var  binding: B
    fun getRetrofit() = BaseApplication.instance.getRetrofit()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResId(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        binding.lifecycleOwner = this
        onViewCreated()
    }

    abstract fun getLayoutResId():Int
    abstract fun onViewCreated()

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