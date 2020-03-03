package com.toolsfordevs.fast.plugins.actions.base

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
abstract class Action<T:Any>(private val defaultValue:(() -> T?)? = null)
{
    var value:T? = defaultValue?.invoke()

    open fun reset()
    {
        value = defaultValue?.invoke()
    }
}