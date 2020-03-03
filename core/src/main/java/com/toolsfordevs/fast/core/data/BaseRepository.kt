package com.toolsfordevs.fast.core.data

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ConcurrentLinkedQueue

abstract class BaseRepository<T>
{
    private val data = ConcurrentLinkedQueue<T>()

    protected val maxEntries: Int = -1

    protected val listeners = arrayListOf<RepositoryListener<T>>()
    protected val changeListeners = arrayListOf<RepositoryChangeListener>()

    val looper = Handler(Looper.getMainLooper())

    open fun add(item: T)
    {
        if (maxEntries > 0 && data.size == maxEntries)
        {
            data.remove()
        }

        data.add(item)

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
        if (data.indexOf(item) != -1)
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
        if (data.indexOf(item) != -1)
        {
            data.remove(item)

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

    fun getData(): List<T>
    {
        return data.toList() // Make a copy, it’s better
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