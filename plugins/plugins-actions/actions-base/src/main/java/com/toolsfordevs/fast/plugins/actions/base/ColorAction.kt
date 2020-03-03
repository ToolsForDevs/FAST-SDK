package com.toolsfordevs.fast.plugins.actions.base

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class ColorAction(defaultValue:(() -> Int?)? = null) : Action<Int>(defaultValue)
{
    constructor(defaultValue: Int?) : this({ defaultValue })
}