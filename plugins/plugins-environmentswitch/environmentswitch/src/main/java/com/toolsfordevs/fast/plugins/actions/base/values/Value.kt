package com.toolsfordevs.fast.plugins.actions.base.values

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
abstract class Value<T>(private val defaultValue: (() -> T?)? = null)
{
    var value: T? = defaultValue?.invoke()

    open fun reset()
    {
        value = defaultValue?.invoke()
    }
}