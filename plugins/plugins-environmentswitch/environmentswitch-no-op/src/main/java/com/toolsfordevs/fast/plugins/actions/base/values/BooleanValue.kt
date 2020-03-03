package com.toolsfordevs.fast.plugins.actions.base.values

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class BooleanValue(defaultValue: (() -> Boolean?)? = null) : Value<Boolean>(defaultValue)
{
    constructor(defaultValue: Boolean) : this({ defaultValue })
}