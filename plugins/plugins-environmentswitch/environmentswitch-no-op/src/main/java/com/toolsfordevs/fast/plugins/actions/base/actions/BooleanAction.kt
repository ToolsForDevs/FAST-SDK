package com.toolsfordevs.fast.plugins.actions.base.actions

import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.actions.base.values.BooleanValue

@Keep
class BooleanAction(name: String, callback: (Boolean?) -> Unit, value:BooleanValue? = null) : Action<Boolean>(name, callback, value)