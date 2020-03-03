package com.toolsfordevs.fast.core.ui.ext

import android.app.Activity
import android.content.ContextWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup?.inflate(layoutRes: Int): View
{
    return LayoutInflater.from(this?.context).inflate(layoutRes, this, false)
}

val View.activity: Activity?
    get() {
        var ctx = context
        while (true) {
            if (ctx !is ContextWrapper)
                return null

            if (ctx is Activity)
                return ctx

            ctx = (ctx as ContextWrapper).baseContext
        }
    }