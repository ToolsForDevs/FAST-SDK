package com.toolsfordevs.fast.plugins.actions.base.actions

import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.actions.base.values.StringValue

@Keep
open class StringAction(name: String, callback: (String?) -> Unit, value: StringValue? = null) : Action<String>(name, callback, value)