package com.jetpack.support.api

import okhttp3.Interceptor
import okhttp3.Response

class HttpRequestHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

//        val originalRequest = chain.request()
//
//        val request = originalRequest.newBuilder()
//                .header("token", BaseApplication.instance.token)
//                .method(originalRequest.method, originalRequest.body)
//                .build()

        return chain.proceed(chain.request())
    }
}