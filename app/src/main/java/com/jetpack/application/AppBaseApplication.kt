package com.jetpack.application

import com.jetpack.support.ui.BaseApplication

class AppBaseApplication: BaseApplication() {
    override fun getBaseRequestUrl(): String = "https://manage-api.kroraina.com.cn/api/"
}