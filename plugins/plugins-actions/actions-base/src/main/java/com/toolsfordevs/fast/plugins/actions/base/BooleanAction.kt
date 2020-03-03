package com.toolsfordevs.fast.plugins.actions.base

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class BooleanAction(defaultValue:(() -> Boolean?)? = null) : Action<Boolean>(defaultValue)
{
    constructor(defaultValue: Boolean) : this({ defaultValue })
}