package com.toolsfordevs.fast.core.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.LayoutInflater
import android.view.ViewGroup


fun Context.inflate(resource:Int, viewGroup: ViewGroup) = LayoutInflater.from(this).inflate(resource, viewGroup)
fun Context.inflate(resource:Int, viewGroup: ViewGroup, attachToRoot:Boolean) = LayoutInflater.from(this).inflate(resource, viewGroup, attachToRoot)

val Context.activity: Activity?
    get()
    {
        var ctx = this
        while (true)
        {
            if (ctx !is ContextWrapper)
                return null

            if (ctx is Activity)
                return ctx

            ctx = (ctx as ContextWrapper).baseContext
        }
    }