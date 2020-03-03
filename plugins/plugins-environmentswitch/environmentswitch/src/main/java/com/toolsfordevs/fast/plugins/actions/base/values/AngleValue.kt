package com.toolsfordevs.fast.plugins.actions.base.values

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
open class AngleValue(defaultValue:(() -> Float?)? = null) : FloatValue(defaultValue, 0f, 359.99f, 2)
{
    constructor(defaultValue: Float?) : this({ defaultValue })
}