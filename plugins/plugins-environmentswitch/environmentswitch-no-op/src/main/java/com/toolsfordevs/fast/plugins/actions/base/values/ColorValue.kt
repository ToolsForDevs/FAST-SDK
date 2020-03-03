package com.toolsfordevs.fast.plugins.actions.base.values

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
open class ColorValue(defaultValue:(() -> Int?)? = null) : IntValue(defaultValue, 0, 0xFFFFFFFF.toInt())
{
    constructor(defaultValue: Int?) : this({ defaultValue })
}