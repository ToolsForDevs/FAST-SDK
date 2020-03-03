package com.toolsfordevs.fast.plugins.logger.modules.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.formatters.FastFormatter

@Keep
abstract class LogRenderer<T : FastFormatter>(val itemView: View)
{
    constructor(parent: ViewGroup, layoutRes: Int) : this(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false))

    abstract fun bind(data: Any?, formatter: T)
}