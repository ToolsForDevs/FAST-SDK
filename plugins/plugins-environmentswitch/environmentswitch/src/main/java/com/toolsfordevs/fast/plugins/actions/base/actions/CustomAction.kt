package com.toolsfordevs.fast.plugins.actions.base.actions

import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.actions.base.values.Value

@Keep
abstract class CustomAction<T:Any>(name: String, callback: (value: T?) -> Unit, value: Value<T>? = null) : Action<T>(name, callback, value)