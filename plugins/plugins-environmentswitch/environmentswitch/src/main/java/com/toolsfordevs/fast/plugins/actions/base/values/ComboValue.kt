package com.toolsfordevs.fast.plugins.actions.base.values

import com.toolsfordevs.fast.plugins.actions.base.actions.Action

class ComboValue(val actions:List<Action<Any>>? = null) : Value<List<Any?>>( { actions?.map { it.value?.value } })
{
    constructor(actions:(() -> List<Action<Any>>)? = null) : this(actions?.invoke() ?: listOf())

    override fun reset()
    {
        actions?.forEach { it.reset() }
        super.reset()
    }
}