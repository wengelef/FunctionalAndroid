package com.wengelef.autil

import android.util.Log

fun Any.e(message: () -> String) {
    Log.e(this.javaClass.simpleName, message())
}