package com.jetpack.application.api

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface AppRetrofitService {
    @POST("msg/read")
    fun messageSign(@Body mMessageSignParameter:Any):Observable<Any>
}

