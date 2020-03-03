package com.toolsfordevs.fast.modules.recyclerview

import com.toolsfordevs.fast.core.annotation.Keep
import kotlin.reflect.KClass

@Keep
class ExpandableList : MutableList<Any>
{
    private val data: MutableList<Any> = arrayListOf()

    private val groupTypes:MutableList<KClass<*>> = arrayListOf()

    private val indexedData:MutableList<Any> = arrayListOf()

    private val map:LinkedHashMap<Any, Group> = LinkedHashMap()

    var listener:ExpandableListListener? = null
    var defaultExpanded = true

    override val size:Int
        get() = indexedData.size

    fun addGroupType(type:KClass<*>)
    {
        groupTypes.add(type)
    }

    fun add(item:Any, notify:Boolean)
    {
        data.add(item)

        val group:Group
        val isGroup = isGroup(item)
        if (isGroup)
        {
            group = Group(defaultExpanded)
            map.put(item, group)
        }
        else
        {
            group = map.get(map.keys.last())!!
            group.items.add(item)
        }

        if (isGroup || group.isExpanded)
        {
            indexedData.add(item)

            if (notify)
                listener?.onItemInserted(indexedData.size - 1)
        }
    }

    fun addAll(items:Collection<Any>, notify:Boolean)
    {
        val size = indexedData.size
        var group:Group? = null

        if (map.isNotEmpty())
        {
            group = map.get(map.keys.last())
        }

        for (item in items)
        {
            if (isGroup(item))
            {
                group = Group(defaultExpanded)
                map.put(item, group)
            }
            else
            {
                group!!.items.add(item)
            }

            if (group.isExpanded)
                indexedData.add(item)
        }

        val newSize = indexedData.size

        if (size != newSize && notify)
            listener?.onItemRangeInserted(IntRange(size, newSize))
    }

    override fun clear()
    {
        data.clear()
        indexedData.clear()
        map.clear()
    }

    override fun get(index:Int):Any
    {
        return indexedData.get(index)
    }

    private fun isGroup(item:Any):Boolean
    {
        for (groupType in groupTypes)
        {
            if (groupType.isInstance(item))
                return true
        }

        return false
    }

    fun isGroup(position:Int):Boolean
    {
        return isGroup(get(position))
    }

    fun toggle(position: Int)
    {
        val item = indexedData.get(position)

        if (!isGroup(item))
            return

        val group = map.get(item)!!

        if (group.isExpanded)
            collapse(position)
        else
            expand(position)
    }

    fun isExpanded(position:Int):Boolean
    {
        val item = indexedData.get(position)

        if (!isGroup(item))
            return false

        val group = map.get(item)!!

        return group.isExpanded
    }

    fun expand(index:Int)
    {
        val item = indexedData.get(index)

        if (!isGroup(item))
            return

        val group = map.get(item)!!
        group.isExpanded = true

        indexedData.addAll(index + 1, group.items)
        listener?.onItemRangeInserted(IntRange(index+1, index + group.items.size))

        if (indexedData.size > index + group.items.size + 1)
            listener?.onItemChanged(index + group.items.size + 1)
    }

    fun collapse(index:Int)
    {
        val item = indexedData.get(index)

        if (!isGroup(item))
            return

        val group = map.get(item)!!
        group.isExpanded = false

        // indexedData.removeAll(group.items) // Not the same behavior as for loop below...

        // for (item in group.items) // Still bad behavior, seems to remove items based on equals(), not instances
        //     indexedData.remove(item)

        // Do some indexed remove
        val children = group.items.size
        for (i in 0 until children)
            indexedData.removeAt(index + 1)

        listener?.onItemRangeRemoved(IntRange(index + 1, index/* + 1*/ + group.items.size))

        if (indexedData.size > index + 1)
            listener?.onItemChanged(index + 1)
    }

    // data class Group(val item:Any, var isExpanded:Boolean = true)
    data class Group(var isExpanded:Boolean = true, val items:ArrayList<Any> = arrayListOf())

    interface ExpandableListListener
    {
        fun onItemInserted(index:Int)
        fun onItemRangeInserted(range:IntRange)
        fun onItemRemoved(index:Int)
        fun onItemRangeRemoved(range:IntRange)
        fun onItemChanged(index:Int)
    }

    override fun contains(element: Any): Boolean
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun containsAll(elements: Collection<Any>): Boolean
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun indexOf(element: Any): Int
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEmpty(): Boolean
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun iterator(): MutableIterator<Any>
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lastIndexOf(element: Any): Int
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(element: Any): Boolean
    {
        add(element, false)
        return true
    }

    override fun add(index: Int, element: Any)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addAll(index: Int, elements: Collection<Any>): Boolean
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addAll(elements: Collection<Any>): Boolean
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun listIterator(): MutableListIterator<Any>
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun listIterator(index: Int): MutableListIterator<Any>
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(element: Any): Boolean
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeAll(elements: Collection<Any>): Boolean
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeAt(index: Int): Any
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun retainAll(elements: Collection<Any>): Boolean
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun set(index: Int, element: Any): Any
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<Any>
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}