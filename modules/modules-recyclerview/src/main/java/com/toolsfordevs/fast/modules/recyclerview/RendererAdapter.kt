package com.toolsfordevs.fast.modules.recyclerview

import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KClass

@Keep
open class RendererAdapter : RecyclerView.Adapter<Renderer<Any>>(), DataAdapter
{
    companion object
    {
        const val MODE_TYPE = 0
        const val MODE_INSTANCE_OF = 1
    }
    protected val DEFAULT_TYPE = -1

    protected val viewHolderList: MutableList<(parent: ViewGroup) -> Renderer<*>> = mutableListOf()
    protected val typeMap: MutableMap<KClass<*>, Int> = LinkedHashMap()

    protected open val data: MutableList<Any> = mutableListOf()
        /*set(value)
        {
            field = value
            notifyDataSetChanged()
            liveData.value = value
        }*/

    protected open val delegate = object : AdapterDelegate {
        override fun getItem(position: Int): Any
        {
            return data.get(position)
        }
    }

    var mode = MODE_TYPE

    fun getItems():List<Any>
    {
        return data.toList()
    }

    open fun add(element: Any, notify: Boolean = true)
    {
        data.add(element)

        if (notify)
            notifyItemInserted(data.size - 1)
    }

    open fun add(element: Any, position:Int, notify: Boolean = true)
    {
        data.add(position, element)

        if (notify)
            notifyItemInserted(position)
    }

    open fun addAll(elements: Collection<Any>, notify: Boolean = true)
    {
        val start = data.size

        data.addAll(elements)

        if (notify)
            notifyItemRangeInserted(start, elements.size)
    }

    open fun update(item:Any, notify: Boolean)
    {
        update(data.indexOf(item), item, notify)
    }

    open fun update(index:Int, item:Any, notify: Boolean = true)
    {
        if (index >= 0 && index < data.size - 1)
        {
            data[index] = item

            if (notify)
                notifyItemChanged(index)
        }
    }

    fun remove(element: Any, notify: Boolean = true)
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
    }

    fun clear()
    {
        data.clear()
        notifyDataSetChanged()
    }

    inline fun <reified T : Any> addRenderer(noinline factory:(parent:ViewGroup) -> Renderer<T>)
    {
        addRenderer(T::class, factory)
    }

    fun addRenderer(type: KClass<*>, factory: (parent: ViewGroup) -> Renderer<*>)
    {
        viewHolderList.add(factory)
        typeMap[type] = viewHolderList.size - 1
    }

    override fun getItemViewType(position: Int): Int
    {
        if (mode == MODE_INSTANCE_OF)
        {
            val item = data[position]

            for (type in typeMap.keys.reversed())
            {
                if (type.isInstance(item))
                    return typeMap.getValue(type)
            }
        }
        else
        {
            val type: KClass<Any> = data[position].javaClass.kotlin

            if (typeMap.containsKey(type))
                return typeMap.getValue(type)
        }

        Log.w("RendererAdapter", "Can't get view type for object ${data.get(position)}")

        return DEFAULT_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Renderer<Any>
    {
        return if (viewType == DEFAULT_TYPE)
        {
            Log.w("RendererAdapter", "A renderer is missing in adapter, cannot render view for some type of data")
            DummyRenderer(parent)
        }
        else
        {
            @Suppress("UNCHECKED_CAST")
            val renderer = viewHolderList[viewType](parent) as Renderer<Any>
            renderer.adapterDelegate = delegate
            renderer
        }
    }

    override fun onBindViewHolder(holder: Renderer<Any>, position: Int)
    {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int
    {
        return data.size
    }

    override fun getDataAtPosition(position: Int): Any?
    {
        if (position >= 0 && position < data.size)
            return data[position]

        return null
    }

    class DummyRenderer(parent: ViewGroup) : ViewRenderer<Any, View>(View(parent.context))
    {
        init
        {
            itemView.setBackgroundColor(0xFFFF0000.toInt())
        }

        override fun bind(data: Any, position: Int)
        {
            // Do nothing
        }
    }
}
