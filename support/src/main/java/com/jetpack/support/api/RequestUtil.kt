package com.jetpack.support.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.jetpack.support.util.isNetConnected
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.trello.rxlifecycle4.components.support.RxFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


fun RxAppCompatActivity.sendRequest(
    isWhole: Boolean,
    responseClass:Class<*>,
    onCreateDisposable: (mDisposable: Disposable?) -> Unit,
    onSuccess:(it:Any) ->Unit,
    onComplete: () -> Unit,
    requests: MutableLiveData<ArrayList<Observable<*>?>>
) {

    requests.observe(this) {
        if (isNetConnected()) {
            if (requests.value.isNullOrEmpty()) {
                onComplete()
            } else {
                onCreateDisposable(
                    it.first()?.subscribeOn(Schedulers.io())
                        ?.unsubscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.compose(bindToLifecycle())
                        ?.subscribe({

                            try {
                                responseClass.getDeclaredField("m${it.javaClass.simpleName}").apply {
                                    isAccessible = true
                                    responseClass.getDeclaredField("m${it.javaClass.simpleName}").type.getDeclaredMethod("setValue",Any::class.java).invoke(get(null),it)
                                }
                            }catch (e:Exception){ }

                            onSuccess(it)

                            requests.value = requests.value?.apply {
                                removeAt(0)
                            }
                        }, {
                            it.printStackTrace()
                            if (isWhole) {
                                Snackbar.make(
                                    window.decorView,
                                    "网络请求失败，请稍候重试",
                                    Snackbar.LENGTH_LONG
                                )
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
        } else {
            Snackbar.make(window.decorView, "当前未连接网络，请稍候重试", Snackbar.LENGTH_LONG)
                .apply {
                    view.elevation = 0f
                }.show()
        }
    }

}

fun RxFragment.sendRequest(
    isWhole: Boolean,
    responseClass:Class<*>,
    onCreateDisposable: (mDisposable: Disposable?) -> Unit,
    onSuccess:(it:Any) ->Unit,
    onComplete: () -> Unit,
    requests: MutableLiveData<ArrayList<Observable<*>?>>
) {

    requests.observe(this) {
        if (requireContext().isNetConnected()) {
            if (requests.value.isNullOrEmpty()) {
                onComplete()
            } else {
                onCreateDisposable(
                    it.first()?.subscribeOn(Schedulers.io())
                        ?.unsubscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.compose(bindToLifecycle())
                        ?.subscribe({
                            try {
                                responseClass.getDeclaredField("m${it.javaClass.simpleName}").apply {
                                    isAccessible = true
                                    responseClass.getDeclaredField("m${it.javaClass.simpleName}").type.getDeclaredMethod("setValue",Any::class.java).invoke(get(null),it)
                                }
                            }catch (e:Exception){}

                            onSuccess(it)

                            requests.value = requests.value?.apply {
                                removeAt(0)
                            }
                        }, {
                            it.printStackTrace()
                            if (isWhole) {
                                Snackbar.make(
                                    requireActivity().window.decorView,
                                    "网络请求失败，请稍候重试",
                                    Snackbar.LENGTH_LONG
                                )
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
        } else {
            Snackbar.make(requireActivity().window.decorView, "当前未连接网络，请稍候重试", Snackbar.LENGTH_LONG)
                .apply {
                    view.elevation = 0f
                }.show()
        }
    }

}

