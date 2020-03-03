package com.toolsfordevs.fast.plugins.actions.base.values

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
open class StringValue(defaultValue: (() -> String?)? = null) : Value<String>(defaultValue)
{
    constructor(defaultValue: String?) : this({ defaultValue })
}