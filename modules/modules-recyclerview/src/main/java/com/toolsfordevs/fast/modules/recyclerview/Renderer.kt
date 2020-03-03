package com.toolsfordevs.fast.modules.recyclerview

import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.core.ui.ext.inflate


/*abstract class Renderer<T>(view:View) : RecyclerView.ViewHolder(view)
{
    abstract fun bindView(view:View, data: T, position: Int)
}*/
@Keep
abstract class Renderer<T>(view:View) : RecyclerView.ViewHolder(view)
{
    open var adapterDelegate:AdapterDelegate? = null

    constructor(parent: ViewGroup, layoutRes: Int) : this(parent.inflate(layoutRes))

    abstract fun bind(data: T, position: Int)

    fun getItem(position:Int):Any?
    {
        return adapterDelegate?.getItem(position)
    }
}

@Keep
interface AdapterDelegate
{
    fun getItem(position:Int):Any
}