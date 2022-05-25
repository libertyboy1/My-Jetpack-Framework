package com.jetpack.support.ui

import android.app.Application
import com.jetpack.support.api.ApiManager
import com.orhanobut.logger.*
import retrofit2.Retrofit


abstract class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
    }


    abstract fun getBaseRequestUrl(): String

    fun getRetrofit(): Retrofit = ApiManager.getInstance().getRetrofit(getBaseRequestUrl())

    override fun onCreate() {
        super.onCreate()
        instance = this

        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .build()

        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return !BuildConfig.DEBUG
            }
        })
    }

}