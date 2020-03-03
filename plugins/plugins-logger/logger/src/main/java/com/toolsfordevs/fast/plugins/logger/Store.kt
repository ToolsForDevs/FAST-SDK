package com.toolsfordevs.fast.plugins.logger

import android.util.Log
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.plugins.logger.ui.Prefs
import com.toolsfordevs.fast.plugins.logger.model.Priority
import com.toolsfordevs.fast.plugins.logger.model.LogEntry
import com.toolsfordevs.fast.plugins.logger.model.LogSession
import com.toolsfordevs.fast.plugins.logger.ui.prefs.FilterProfile
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentLinkedQueue

internal object Store
{
    private val logs: ConcurrentLinkedQueue<Any> = ConcurrentLinkedQueue()

    private val maxEntries = Prefs.maxEntries

    private val listeners: ArrayList<WeakReference<LogRepositoryListener>> = arrayListOf()

    private var session: LogSession? = null

    fun addLog(logEntry: LogEntry)
    {
        if (logs.size == maxEntries) logs.remove()

        if (session != null) session!!.addEntry(logEntry)
        else logs.add(logEntry)

        if (Prefs.sendToLogcat)
        {
            val tag = logEntry.tag ?: "NO_TAG"
            val message = logEntry.data?.toString() ?: "null"

            when (logEntry.priority)
            {
                Priority.DEFAULT -> Log.v(tag, message)
                Priority.DEBUG   -> Log.d(tag, message)
                Priority.INFO    -> Log.i(tag, message)
                Priority.WARNING -> Log.w(tag, message)
                Priority.ERROR   -> Log.e(tag, message)
                Priority.WTF     -> Log.wtf(tag, message)
            }
        }

        if (check(
                        logEntry,
                        Prefs.currentProfiles
                )
        )
            for (listener in listeners)
                listener.get()?.onLogEntryAdded(logEntry, session)
    }

    fun startSession(name: String, color: Int = FastColor.colorAccent)
    {
        stopSession() // Stop current session before starting a new one

        session = LogSession(name, color)
        logs.add(session)

        for (listener in listeners) listener.get()?.onSessionStarted(session!!)
    }

    fun stopSession()
    {
        session?.let {
            it.hasEnded = true

            for (listener in listeners) listener.get()?.onSessionEnded(it)
        }
        session = null
    }

    fun clear()
    {
        logs.clear()
    }

    fun getLogs(): List<Any>
    {
        // That's where the filtering happens
        val profiles = Prefs.currentProfiles
        Log.d("LoggerRepository","getLogs profiles ${profiles.count()}")
        return logs.toList()
            .filter { if (it is LogEntry) check(it, profiles) else true }
            .map {
                if (it is LogSession)
                {
                    LogSession(it.name, it.color).apply {
                        hasEnded = it.hasEnded
                        for (entry in it.getEntries())
                        {
                            if (check(entry, profiles))
                                addEntry(entry)
                        }
                    }
                }
                else it
            }
    }

    private fun check(entry: LogEntry, profiles: List<FilterProfile>): Boolean
    {
        if (profiles.isEmpty()) return true

        main@ for (profile in profiles)
        {
            if (!profile.priorities.contains(entry.priority.value))
                continue@main

            if (entry.tag != null)
            {
                for (tag in profile.tags) if (!tag.check(entry.tag))
                    continue@main
            }

            // We checked all the conditions for at least one profile
            // so we're good

            return true
        }

        // No profile passed the test, we're not good
        return false
    }

    fun addListener(listener: LogRepositoryListener)
    {
        listeners.add(WeakReference(listener))
    }

    fun removeListener(listener: LogRepositoryListener)
    {
        for (refListener in listeners)
        {
            if (refListener.get() == listener)
            {
                listeners.remove(refListener)
                break
            }
        }
    }

    interface LogRepositoryListener
    {
        fun onLogEntryAdded(logEntry: LogEntry, session: LogSession?)
        fun onSessionStarted(session: LogSession)
        fun onSessionEnded(session: LogSession)
    }
}