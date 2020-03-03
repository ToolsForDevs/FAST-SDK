package com.toolsfordevs.fast.plugins.actions.base

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class IntAction(defaultValue:(() -> Int?)? = null, val min:Int = 0, val max:Int = 100) : Action<Int>(defaultValue)
{
    constructor(defaultValue: Int?, min:Int = 0, max:Int = 100) : this({ defaultValue }, min, max)
}