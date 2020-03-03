package com.toolsfordevs.fast.plugins.actions.base

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
abstract class SimpleCustomAction(name: String, callback: () -> Unit) : CustomAction<Any>(name, SimpleAction(), { _ -> callback.invoke() })
{
    internal class SimpleAction : Action<Any>()
}