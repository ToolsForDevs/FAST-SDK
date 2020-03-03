package com.toolsfordevs.fast.plugins.actions.base

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class StringAction(defaultValue: (() -> String?)? = null) : Action<String>(defaultValue)
{
    constructor(defaultValue: String?) : this({ defaultValue })
}