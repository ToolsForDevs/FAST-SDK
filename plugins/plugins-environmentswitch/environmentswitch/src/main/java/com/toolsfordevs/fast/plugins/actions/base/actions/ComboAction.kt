package com.toolsfordevs.fast.plugins.actions.base.actions

import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.actions.base.values.ComboValue

@Keep
class ComboAction(name: String, callback: (List<Any?>?) -> Unit, value: ComboValue? = null) : Action<List<Any?>>(name, callback, value )