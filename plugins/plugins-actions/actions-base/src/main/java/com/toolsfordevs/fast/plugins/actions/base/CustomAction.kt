package com.toolsfordevs.fast.plugins.actions.base

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
abstract class CustomAction<T:Any>(val name: String, val action: Action<T>, val callback: (value: T?) -> Unit)