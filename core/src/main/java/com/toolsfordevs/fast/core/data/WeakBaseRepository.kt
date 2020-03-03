package com.toolsfordevs.fast.core.data

import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentLinkedQueue

// ToDo Move to core
abstract class WeakBaseRepository<T>
{
    private val data = ConcurrentLinkedQueue<WeakReference<T>>()

    protected val maxEntries: Int = -1

    protected val listeners = arrayListOf<RepositoryListener<T>>()
    protected val changeListeners = arrayListOf<RepositoryChangeListener>()

    private val looper = Handler(Looper.getMainLooper())

    open fun add(item: T)
    {
        clean()

        if (maxEntries > 0 && data.size == maxEntries)
            data.remove()

        data.add(WeakReference(item))

        // Deliver on main thread
        looper.post {
            for (listener in listeners)
                listener.onItemAdded(item)

            for (listener in changeListeners)
                listener.onChange()
        }

    }

    fun onItemUpdated(item: T)
    {
        if (data.map { it.get() }.indexOf(item) != -1)
        {
            // Deliver on main thread
            looper.post {
                for (listener in listeners)
                    listener.onItemUpdated(item)

                for (listener in changeListeners)
                    listener.onChange()
            }
        }
    }

    open fun remove(item: T)
    {
        val found = data.find { it.get() == item }

        if (found != null)
        {
            data.remove(found)

            // Deliver on main thread
            looper.post {
                for (listener in listeners)
                    listener.onItemRemoved(item)

                for (listener in changeListeners)
                    listener.onChange()
            }
        }
    }

    open fun clear()
    {
        data.clear()

        // Deliver on main thread
        looper.post {
            for (listener in changeListeners)
                listener.onChange()
        }
    }

    private fun clean()
    {
        val each = data.iterator()
        while (each.hasNext())
        {
            if (each.next().get() == null)
                each.remove()
        }
    }

    fun getData(): List<T>
    {
        clean()
        return data.map { it.get()!! } // Make a copy, it’s better
    }

    fun addListener(listener: RepositoryListener<T>)
    {
        listeners.add(listener)
    }

    fun removeListener(listener: RepositoryListener<T>)
    {
        listeners.remove(listener)
    }

    fun addListener(listener: RepositoryChangeListener)
    {
        changeListeners.add(listener)
    }

    fun removeListener(listener: RepositoryChangeListener)
    {
        changeListeners.remove(listener)
    }

    interface RepositoryListener<T>
    {
        fun onItemAdded(item: T)
        fun onItemUpdated(item: T)
        fun onItemRemoved(item: T)
    }

    interface RepositoryChangeListener
    {
        fun onChange()
    }
}