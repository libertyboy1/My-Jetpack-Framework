package com.jetpack.application.api

import com.jetpack.application.BuildConfig
import com.jetpack.application.model.AppStatusEntity
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface AppRetrofitService {
    private val headerMap: HashMap<String, String>
        get() = hashMapOf(
            Pair("X-Authorization-With",  "1111111"),
            Pair("c-app-ver", BuildConfig.VERSION_NAME),
            Pair("c-app-platform", "android")
        )

    //功能隐藏
    @GET("mobile/appStatus")
    fun getAppStatus(
        @HeaderMap hashMap: HashMap<String, String> = headerMap
    ): Observable<AppStatusEntity>

}

