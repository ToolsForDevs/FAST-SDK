package com.toolsfordevs.fast.plugins.actions.base.values

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
open class IntValue(defaultValue:(() -> Int?)? = null, val min:Int = 0, val max:Int = 100) : Value<Int>(defaultValue)
{
    constructor(defaultValue: Int?, min:Int = 0, max:Int = 100) : this({ defaultValue }, min, max)
}