package com.toolsfordevs.fast.plugins.viewinspector.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
abstract class ViewPropertyRenderer<T : ViewProperty<*, *>>(val itemView: View)
{
    constructor(parent: ViewGroup, layoutRes: Int) : this(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false))

    abstract fun bind(data: T)
}