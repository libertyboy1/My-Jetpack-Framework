package com.jetpack.support.api

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.jetpack.support.util.isNetConnected
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.trello.rxlifecycle4.components.support.RxFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * 发送网络请求
 *
 * @param isWhole  多网络请求是否为一个整体，true：一个整体，请求之间相互影响，一个失败则全部失败；false：请求之间相互独立，失败不影响其他请求
 * @param onSuccess  每个请求的成功回调
 * @param onError  每个请求的失败回调
 * @param onComplete   全部请求请求结束时回调
 * @param args  网络请求
 */


fun RxAppCompatActivity.sendRequest(
    isWhole: Boolean,
    onCreateDisposable: (mDisposable: Disposable?) -> Unit,
    onSuccess: (it: Any) -> Unit,
    onComplete: () -> Unit,
    requests: MutableLiveData<ArrayList<Observable<*>?>>
) {

    requests.observe(this) {
        if (isNetConnected()){
            if (requests.value.isNullOrEmpty()) {
                onComplete()
            } else {
                onCreateDisposable(
                    it.first()?.subscribeOn(Schedulers.io())
                        ?.unsubscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.compose(bindToLifecycle())
                        ?.subscribe({
                            onSuccess(it)
                            requests.value = requests.value?.apply {
                                removeAt(0)
                            }
                        }, {
                            if (isWhole) {
                                Snackbar.make(window.decorView, "网络请求失败，请稍候重试", Snackbar.LENGTH_LONG)
                                    .apply {
                                        view.elevation = 0f
                                    }.show()
                                onComplete()
                            } else {
                                requests.value = requests.value?.apply {
                                    removeAt(0)
                                }
                            }
                        })
                )
            }
        }else{
            Snackbar.make(window.decorView, "当前未连接网络，请稍候重试", Snackbar.LENGTH_LONG)
                .apply {
                    view.elevation = 0f
                }.show()
        }
    }

}

fun RxFragment.sendRequest(
    isWhole: Boolean,
    onCreateDisposable: (mDisposable: Disposable?) -> Unit,
    onSuccess: (it: Any) -> Unit,
    onComplete: () -> Unit,
    requests: MutableLiveData<ArrayList<Observable<*>?>>
) {

    requests.observe(this) {
        if (requireContext().isNetConnected()){
            if (requests.value.isNullOrEmpty()) {
                onComplete()
            } else {
                onCreateDisposable(
                    it.first()?.subscribeOn(Schedulers.io())
                        ?.unsubscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.compose(bindToLifecycle())
                        ?.subscribe({
                            onSuccess(it)
                            requests.value = requests.value?.apply {
                                removeAt(0)
                            }
                        }, {
                            if (isWhole) {
                                Snackbar.make(requireActivity().window.decorView, "网络请求失败，请稍候重试", Snackbar.LENGTH_LONG)
                                    .apply {
                                        view.elevation = 0f
                                    }.show()
                                onComplete()
                            } else {
                                requests.value = requests.value?.apply {
                                    removeAt(0)
                                }
                            }
                        })
                )
            }
        }else{
            Snackbar.make(requireActivity().window.decorView, "当前未连接网络，请稍候重试", Snackbar.LENGTH_LONG)
                .apply {
                    view.elevation = 0f
                }.show()
        }
    }

}

