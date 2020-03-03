package com.toolsfordevs.fast.plugins.actions.base.values

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
open class LongValue(defaultValue:(() -> Long?)? = null, val min:Long = 0, val max:Long = 100) : Value<Long>(defaultValue)
{
    constructor(defaultValue: Long?, min:Long = 0, max:Long = 100) : this({ defaultValue }, min, max)
}