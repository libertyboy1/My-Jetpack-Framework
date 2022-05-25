package com.jetpack.support.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.MutableLiveData
import com.jetpack.support.BaseViewModel
import com.jetpack.support.api.sendRequest
import com.jetpack.support.presenter.BasePresenter
import com.trello.rxlifecycle4.components.support.RxFragment
import io.reactivex.rxjava3.core.Observable

abstract class SupportFragment<A : AppCompatActivity, P : BasePresenter, V : BaseViewModel, B : ViewDataBinding> :
    RxFragment() {
    fun getParentActivity(): A = requireActivity() as A
    abstract fun getPresenterInstance(): P
    val mPresenter: P by lazy { getPresenterInstance() }
    abstract val vm: V
    lateinit var binding: B
    fun getRetrofit() = BaseApplication.instance.getRetrofit()
    private var requestIsWhole = true
    val requests: MutableLiveData<ArrayList<Observable<*>?>> by lazy {
        MutableLiveData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        binding.lifecycleOwner = this
        onViewCreated()
        mPresenter.doEvent()

//        getParentActivity().javaClass.declaredMethods.forEach {
//            Log.e("suaobo","${it.name}")
//        }




        requests.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                sendRequest(
                    requestIsWhole,
                    Class.forName("${getParentActivity().javaClass.getDeclaredMethod("getResponseClass").invoke(getParentActivity()) as String}"),
                    { mPresenter.currentRequestDisposable = it },
                    {mPresenter.onRequestSuccess(it)},
                    { mPresenter.onRequestComplete() },
                    requests
                )
            }
        }

    }

    abstract fun getLayoutResId(): Int
    abstract fun onViewCreated()

    fun setRequestIsWhole(requestIsWhole:Boolean){
        this.requestIsWhole = requestIsWhole
    }

    fun sendRequest(vararg requests:Observable<*>?){
        sendRequest(
            requestIsWhole,
            Class.forName("${getParentActivity().javaClass.getDeclaredMethod("getResponseClass").invoke(getParentActivity()) as String}"),
            { mPresenter.currentRequestDisposable = it },
            {mPresenter.onRequestSuccess(it)},
            { mPresenter.onRequestComplete() },
            MutableLiveData<ArrayList<Observable<*>?>>().apply {
                value = ArrayList(requests.asList())
            }
        )
    }

}