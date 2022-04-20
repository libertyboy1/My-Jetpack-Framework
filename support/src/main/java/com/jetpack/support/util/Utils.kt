package com.jetpack.support.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Parcelable
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.io.Serializable

fun EditText.action(actionId: Int, func: () -> Unit) {
    setOnEditorActionListener { _, receivedActionId, _ ->
        if (actionId == receivedActionId) {
            func()
        }
        true
    }
}

fun Context.isNetConnected(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = manager.getNetworkCapabilities(manager.activeNetwork)
    return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) ?: false
}

inline fun <reified F : Fragment> getFragmentInstance(
    vararg params: Pair<String, Any>
): F {
    val fragment = F::class.java.newInstance()
    val bundle = Bundle()
    if (params.isNotEmpty()) {
        params.forEach {
            when (val value = it.second) {
                is Int -> bundle.putInt(it.first, value)
                is Long -> bundle.putLong(it.first, value)
                is CharSequence -> bundle.putCharSequence(it.first, value)
                is String -> bundle.putString(it.first, value)
                is Float -> bundle.putFloat(it.first, value)
                is Double -> bundle.putDouble(it.first, value)
                is Char -> bundle.putChar(it.first, value)
                is Short -> bundle.putShort(it.first, value)
                is Boolean -> bundle.putBoolean(it.first, value)
                is Serializable -> bundle.putSerializable(it.first, value)
                is Parcelable -> bundle.putParcelable(it.first, value)
            }
            return@forEach
        }
    }
    fragment.arguments = bundle
    return fragment
}

inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivityForResult(
    vararg params: Pair<String, Any>,
    crossinline onResult: (result: ActivityResult) -> Unit
) {

    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            onResult(it)
    }.apply {
        launch(Intent(this@startActivityForResult, T::class.java).apply {
            if (params.isNotEmpty()) {
                params.forEach {
                    when (val value = it.second) {
                        is Int -> putExtra(it.first, value)
                        is Long -> putExtra(it.first, value)
                        is CharSequence -> putExtra(it.first, value)
                        is String -> putExtra(it.first, value)
                        is Float -> putExtra(it.first, value)
                        is Double -> putExtra(it.first, value)
                        is Char -> putExtra(it.first, value)
                        is Short -> putExtra(it.first, value)
                        is Boolean -> putExtra(it.first, value)
                        is Serializable -> putExtra(it.first, value)
                        is Bundle -> putExtra(it.first, value)
                        is Parcelable -> putExtra(it.first, value)
                        is Array<*> -> when {
                            value.isArrayOf<CharSequence>() -> putExtra(it.first, value)
                            value.isArrayOf<String>() -> putExtra(it.first, value)
                            value.isArrayOf<Parcelable>() -> putExtra(it.first, value)
                        }
                        is IntArray -> putExtra(it.first, value)
                        is LongArray -> putExtra(it.first, value)
                        is FloatArray -> putExtra(it.first, value)
                        is DoubleArray -> putExtra(it.first, value)
                        is CharArray -> putExtra(it.first, value)
                        is ShortArray -> putExtra(it.first, value)
                        is BooleanArray -> putExtra(it.first, value)
                    }
                    return@forEach
                }
            }
        })
    }

}


inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivity(vararg params: Pair<String, Any>) {

    startActivity(Intent(this, T::class.java).apply {
        if (params.isNotEmpty()) {
            params.forEach {
                when (val value = it.second) {
                    is Int -> putExtra(it.first, value)
                    is Long -> putExtra(it.first, value)
                    is CharSequence -> putExtra(it.first, value)
                    is String -> putExtra(it.first, value)
                    is Float -> putExtra(it.first, value)
                    is Double -> putExtra(it.first, value)
                    is Char -> putExtra(it.first, value)
                    is Short -> putExtra(it.first, value)
                    is Boolean -> putExtra(it.first, value)
                    is Serializable -> putExtra(it.first, value)
                    is Bundle -> putExtra(it.first, value)
                    is Parcelable -> putExtra(it.first, value)
                    is Array<*> -> when {
                        value.isArrayOf<CharSequence>() -> putExtra(it.first, value)
                        value.isArrayOf<String>() -> putExtra(it.first, value)
                        value.isArrayOf<Parcelable>() -> putExtra(it.first, value)
                    }
                    is IntArray -> putExtra(it.first, value)
                    is LongArray -> putExtra(it.first, value)
                    is FloatArray -> putExtra(it.first, value)
                    is DoubleArray -> putExtra(it.first, value)
                    is CharArray -> putExtra(it.first, value)
                    is ShortArray -> putExtra(it.first, value)
                    is BooleanArray -> putExtra(it.first, value)
                }
                return@forEach
            }
        }
    })
}


fun Context.dp2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun Context.px2dp(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}