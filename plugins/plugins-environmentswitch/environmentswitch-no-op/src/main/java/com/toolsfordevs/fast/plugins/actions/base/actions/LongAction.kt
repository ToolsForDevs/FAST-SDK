package com.toolsfordevs.fast.plugins.actions.base.actions

import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.actions.base.values.LongValue

@Keep
open class LongAction(name: String, callback: (Long?) -> Unit, value: LongValue? = null) : Action<Long>(name, callback, value)