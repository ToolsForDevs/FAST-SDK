package com.toolsfordevs.fast.plugins.actions.ui

import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.plugins.actions.base.Action


internal abstract class ActionDelegate<R:Any, T:Action<R>>
{
    private lateinit var action:T

    var listener:((value:R?)->Unit)? = null

    abstract fun createView(parent: ViewGroup): View

    internal fun internalBind(action:T)
    {
        this.action = action
        bind(action)
    }

    abstract fun bind(action: T)

    fun onValueChange()
    {
        listener?.invoke(action.value)
    }


}