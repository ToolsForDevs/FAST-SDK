package com.toolsfordevs.fast.plugins.actions.base.values

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
open class AlphaValue(defaultValue:(() -> Float?)? = null) : FloatValue(defaultValue, 0f, 1f, 2)
{
    constructor(defaultValue: Float?) : this({ defaultValue })
}