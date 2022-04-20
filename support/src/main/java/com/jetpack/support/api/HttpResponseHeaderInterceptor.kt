package com.jetpack.support.api

import okhttp3.Interceptor
import okhttp3.Response

class HttpResponseHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

//        val originalResponse = chain.proceed(chain.request())

//            if (!originalResponse.header("token").isNullOrEmpty()) {
//                BaseApplication.instance.token = originalResponse.header("token")!!
//            }

        return chain.proceed(chain.request())
    }
}