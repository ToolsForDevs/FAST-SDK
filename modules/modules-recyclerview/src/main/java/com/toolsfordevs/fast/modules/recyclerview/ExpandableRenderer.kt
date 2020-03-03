package com.toolsfordevs.fast.modules.recyclerview

import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.core.ui.ext.inflate

@Keep
abstract class ExpandableRenderer<T>(view: View) : Renderer<T>(view)
{
    constructor(parent: ViewGroup, layoutRes: Int) : this(parent.inflate(layoutRes))

    fun toggle()
    {
        (adapterDelegate as ExpandableAdapterDelegate).toggle(adapterPosition)
    }

    fun isExpanded():Boolean
    {
        return (adapterDelegate as ExpandableAdapterDelegate).isExpanded(adapterPosition)
    }

    fun shouldShowDivider():Boolean
    {
        return (adapterDelegate as ExpandableAdapterDelegate).shouldShowGroupDivider(adapterPosition)
    }
}

@Keep
interface ExpandableAdapterDelegate : AdapterDelegate
{
    fun toggle(position:Int)
    fun isExpanded(position:Int):Boolean
    fun shouldShowGroupDivider(position:Int):Boolean
}