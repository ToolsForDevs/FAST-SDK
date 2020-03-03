package com.toolsfordevs.fast.plugins.actions.base

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class AlphaAction(defaultValue:(() -> Float?)? = null) : FloatAction(defaultValue, 0f, 1f, 2)
{
    constructor(defaultValue: Float?) : this({ defaultValue })
}