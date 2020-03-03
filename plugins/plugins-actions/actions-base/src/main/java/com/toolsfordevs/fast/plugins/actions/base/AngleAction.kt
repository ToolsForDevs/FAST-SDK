package com.toolsfordevs.fast.plugins.actions.base

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class AngleAction(defaultValue:(() -> Float?)? = null) : FloatAction(defaultValue, 0f, 359.99f, 2)
{
    constructor(defaultValue: Float?) : this({ defaultValue })
}