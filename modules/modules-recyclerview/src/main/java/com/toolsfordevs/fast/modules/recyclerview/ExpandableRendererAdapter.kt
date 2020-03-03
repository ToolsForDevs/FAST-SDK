package com.toolsfordevs.fast.modules.recyclerview

import android.view.ViewGroup
import com.toolsfordevs.fast.core.annotation.Keep
import kotlin.reflect.KClass

@Keep
class ExpandableRendererAdapter : RendererAdapter(),
                                  ExpandableList.ExpandableListListener
{
    override val data = ExpandableList()

    override val delegate = object : ExpandableAdapterDelegate
    {
        override fun isExpanded(position: Int): Boolean
        {
            return data.isExpanded(position)
        }

        override fun toggle(position: Int)
        {
            data.toggle(position)
        }

        override fun getItem(position: Int): Any
        {
            return data.get(position)
        }

        override fun shouldShowGroupDivider(position:Int): Boolean
        {
            if (position == 0)
                return false

            if (isExpanded(position))
                return true

            if (!data.isGroup(position - 1))
                return true

            return false
        }
    }

    init
    {
        data.listener = this
    }

    var defaultExpanded = true
        set(value)
        {
            data.defaultExpanded = value
            field = value
        }

    override fun add(element: Any, notify: Boolean)
    {
        data.add(element, notify)
    }

    override fun addAll(elements: Collection<Any>, notify: Boolean)
    {
        data.addAll(elements, notify)
    }

    /*fun remove(element: Any, notify: Boolean = true)
    {
        val position = data.indexOf(element)
        if (position != -1)
        {
            data.removeAt(position)
            if (notify)
                notifyItemRemoved(position)
        }
    }

    fun removeAll(elements: Collection<Any>, notify: Boolean = true)
    {
        elements.forEach { remove(it, notify) }
    }

    fun remove(from: Int, count: Int, notify: Boolean = true)
    {
        val max = Math.min(from + count, data.size)

        for (i in 0 until max)
            data.removeAt(from)

        if (notify)
            notifyItemRangeRemoved(from, max)
    }*/


    inline fun <reified T : Any> addRenderer(noinline factory:(parent:ViewGroup) -> Renderer<T>, isGroup:Boolean = false)
    {
        addRenderer(T::class, factory, isGroup)
    }

    fun addRenderer(type: KClass<*>, factory: (parent: ViewGroup) -> Renderer<*>, isGroup:Boolean = false)
    {
        viewHolderList.add(factory)
        typeMap[type] = viewHolderList.size - 1

        if (isGroup)
            data.addGroupType(type)
    }


    override fun onItemInserted(index: Int)
    {
        notifyItemInserted(index)
        // notifyDataSetChanged()
    }

    override fun onItemRangeInserted(range: IntRange)
    {
        notifyItemRangeInserted(range.start, range.last - range.start + 1)
        // notifyDataSetChanged()
    }

    override fun onItemRemoved(index: Int)
    {
        notifyItemRemoved(index)
        // notifyDataSetChanged()
    }

    override fun onItemRangeRemoved(range: IntRange)
    {
        notifyItemRangeRemoved(range.start, range.last - range.start + 1)
        // notifyDataSetChanged()
    }

    override fun onItemChanged(index: Int)
    {
        notifyItemChanged(index)
        // notifyDataSetChanged()
    }
}
