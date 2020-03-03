package com.toolsfordevs.fast.plugins.actions.base.actions

import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.actions.base.values.FloatValue

@Keep
open class FloatAction(name: String, callback: (Float?) -> Unit, value: FloatValue? = null) : Action<Float>(name, callback, value)