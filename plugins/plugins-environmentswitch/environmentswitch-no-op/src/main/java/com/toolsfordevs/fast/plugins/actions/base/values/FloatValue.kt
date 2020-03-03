package com.toolsfordevs.fast.plugins.actions.base.values

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
open class FloatValue(defaultValue:(() -> Float?)? = null, val min:Float = 0f, val max:Float = 100f, val decimalPrecision:Int = 2) : Value<Float>(defaultValue)
{
    constructor(defaultValue: Float?, min:Float = 0f, max:Float = 100f, decimalPrecision:Int = 2) : this({ defaultValue }, min, max, decimalPrecision)
}