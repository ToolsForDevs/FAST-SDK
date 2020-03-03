package com.toolsfordevs.fast.plugins.actions.base.actions

import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.actions.base.values.IntValue

@Keep
class IntAction(name: String, callback: (Int?) -> Unit, value: IntValue? = null) : Action<Int>(name, callback, value)