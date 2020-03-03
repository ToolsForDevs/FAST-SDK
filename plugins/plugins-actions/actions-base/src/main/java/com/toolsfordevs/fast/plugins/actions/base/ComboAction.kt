package com.toolsfordevs.fast.plugins.actions.base

import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class ComboAction(val actions: List<ActionItem>) :
    Action<ArrayList<Any?>>({ ArrayList(actions.size) })
{
    constructor(actions: (() -> List<ActionItem>?)? = null) : this(actions?.invoke() ?: arrayListOf())

    init
    {
        for (actionItem in actions)
            value!!.add(actionItem.action.value)
    }

    fun <T : Any> addAction(name: String, action: Action<T>)
    {
        addAction(ActionItem(name, action))
    }

    fun addAction(action: ActionItem)
    {
        if (value == null)
            value = arrayListOf()

        value!!.add(action)
    }

    override fun reset()
    {
        super.reset()

        for (actionItem in actions)
        {
            actionItem.action.reset()
            value!!.add(actionItem.action.value)
        }
    }

    @Keep
    data class ActionItem(val name: String, val action: Action<*>)
}