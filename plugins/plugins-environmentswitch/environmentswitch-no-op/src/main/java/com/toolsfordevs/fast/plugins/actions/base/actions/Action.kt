package com.toolsfordevs.fast.plugins.actions.base.actions

import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.actions.base.values.Value

@Keep
abstract class Action<T : Any>(val name: String, val callback: (T?) -> Unit, open val value: Value<T>? = null)
{
    open fun reset()
    {
        value?.reset()
    }
}