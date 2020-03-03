package com.toolsfordevs.fast.plugins.viewinspector.modules.androidframework.properties

import android.util.ArrayMap
import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.core.util.ViewUtil
import com.toolsfordevs.fast.plugins.viewinspector.ext.properties.SingleChoiceProperty

/**
 * @param type use one constant from IdProperty class
 */
open class IdProperty<T : View>(getter: (T.() -> Int)? = null, setter: (T.(Int) -> Unit)? = null, val type: Int = SIBLINGS) :
        SingleChoiceProperty<T, Int>(getter, setter as (T.(Int?) -> Unit)?)
{
    override fun defineKeyValues(map: ArrayMap<Int, String>)
    {
        val views = when (type)
        {
            SIBLINGS -> getSiblings()
            CHILDREN -> getChildren(view, false)
            PARENTS -> getParents()
            ANY_VIEW -> getHierarchy()
            else -> getHierarchy()
        }

        for (view in views)
        {
            if (view != this.view)
                map.put(view.id, ViewUtil.getIdString(view) + "#" + view::class.java.simpleName)
        }

        if (map.isNotEmpty())
            map.put(0, "NONE")
    }

    private fun getSiblings(): List<View>
    {
        val list = arrayListOf<View>()

        view.parent?.let {
            if (it is ViewGroup)
            {
                for (i in 0 until it.childCount) list.add(it.getChildAt(i))
            }
        }

        return list
    }

    private fun getChildren(view:View, toLeaves:Boolean = false): List<View>
    {
        val list = arrayListOf<View>()

        view?.let {
            if (it is ViewGroup)
            {
                for (i in 0 until it.childCount)
                {
                    val child = it.getChildAt(i)
                    list.add(child)

                    if (toLeaves)
                        list.addAll(getChildren(child, toLeaves))
                }
            }
        }

        return list
    }

    private fun getParents(): List<View>
    {
        val list = arrayListOf<View>()
        var parent = view.getParent()

        while (parent != null && parent is View)
        {
            list.add(0, parent as View)
            parent = parent.getParent()
        }

        return list
    }

    private fun getHierarchy(): List<View>
    {
        val list = arrayListOf<View>()

        val parents = getParents()

        val root = if (parents.isNotEmpty()) parents[0]
        else view

        list.add(root)

        if (view is ViewGroup)
        {
            val group = view as ViewGroup
            for (i in 0 until group.childCount)
            {
                list.addAll(getChildren(group.getChildAt(i), true))
            }
        }

        return list
    }

    companion object
    {
        const val SELECTION_NONE = -1

        const val SIBLINGS = 0
        const val CHILDREN = 1
        const val PARENTS = 2
        const val ANY_VIEW = 3
    }
}
/*/**
 * @param type use one constant from IdProperty class
 */
open class IdProperty<T: View>(getter: (T.() -> Int)? = null, setter: (T.(Int) -> Unit)? = null, val type:Int = SIBLINGS) : ViewProperty<T, Int>(getter,
    setter as (T.(Int?) -> Unit)?)
{
    companion object
    {
        const val SIBLINGS = 0
        const val CHILDREN = 1
        const val PARENTS = 2
        const val ANY_VIEW = 3
    }
}*/
