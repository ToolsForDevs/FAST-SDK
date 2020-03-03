package com.toolsfordevs.fast.plugins.actions.base

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class LongAction(defaultValue:(() -> Long?)? = null, val min:Long = 0, val max:Long = 100) : Action<Long>(defaultValue)
{
    constructor(defaultValue: Long?, min:Long = 0, max:Long = 100) : this({ defaultValue }, min, max)
}