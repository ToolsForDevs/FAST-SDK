package com.toolsfordevs.fast.plugins.actions.ui

import com.toolsfordevs.fast.core.data.WeakBaseRepository
import com.toolsfordevs.fast.plugins.actions.base.Action


internal class Repository : WeakBaseRepository<ActionWrapper<*>>()
{
    //private val viewActions = hashMapOf<WeakReference<View>, ArrayList<ActionWrapper<*>>>()
    //private val viewTypeActions = hashMapOf<KClass<*>, ArrayList<ActionWrapper<*>>>()

    fun addAction(name: String, callback: () -> Unit)
    {
        addAction(name, SimpleAction()) { callback.invoke() }
    }

    fun <T : Any> addAction(name: String, action: Action<T>, callback: (value: T?) -> Unit)
    {
        add(ActionWrapper(name, action, callback))
    }

    internal fun getFavoriteActions(): List<ActionWrapper<*>> =
            getData().filter { FavoriteManager.isFavorite(it.name) }


    /*fun <T : Any> addActionForView(name: String, view: View, action: Action<T>, callback: (value: T?) -> Unit)
    {
        val list = getMappedViewOrCreate(view)
        list.add(ActionWrapper(name, action, callback))
    }

    private fun getMappedViewOrCreate(view: View): ArrayList<ActionWrapper<*>>
    {
        // Search for key
        for (key in viewActions.keys)
        {
            if (key.get() == view) return viewActions.get(key)!!
        }

        // None found, create it
        val list = arrayListOf<ActionWrapper<*>>()
        viewActions.put(WeakReference(view), list)

        return list
    }

    fun <T : Any> addActionForType(name: String, type: KClass<*>, action: Action<T>, callback: (value: T?) -> Unit)
    {
        var list = viewTypeActions.get(type)

        if (list == null)
        {
            list = arrayListOf()
            viewTypeActions.put(type, list)
        }

        list.add(ActionWrapper(name, action, callback))
    }

    internal fun getActionsForView(view: View): List<ActionWrapper<*>>
    {
        val list = arrayListOf<ActionWrapper<*>>()

        for (key in viewActions.keys)
        {
            if (key.get() == view) list.addAll(viewActions.get(key)!!)
        }

        val types = viewTypeActions.get(view::class)

        if (types != null) list.addAll(types)

        return list
    }*/

}

internal class SimpleAction : Action<Any>()