package com.jetpack.support.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger

class ApiManager private constructor() {
    private val DEFAULT_TIMEOUT: Long = 60

    companion object {
        fun getInstance(): ApiManager {
            return InstanceHelper.instance
        }
    }

    private object InstanceHelper {
        val instance: ApiManager =
            ApiManager()
    }

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor { message ->
            try {
               gson.fromJson(message, Objects::class.java)
//                Logger.json()
            } catch (e: Exception) {
//                Logger.d(message)
            }
        }


    private val client = OkHttpClient.Builder()
        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时的时间
        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(HttpRequestHeaderInterceptor())
        .addInterceptor(HttpResponseHeaderInterceptor())
        .build()

    /**
     * 处理时间
     */
    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create()

    /**
     * 获取Retrofit
     */
    fun getRetrofit(baseUrl: String): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    }


}