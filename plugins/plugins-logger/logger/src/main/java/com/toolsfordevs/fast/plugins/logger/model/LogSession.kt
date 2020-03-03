package com.toolsfordevs.fast.plugins.logger.model

import com.toolsfordevs.fast.core.FastColor


internal class LogSession(var name:String = "", var color:Int = FastColor.colorAccent)
{
    private val entries = arrayListOf<LogEntry>()

    var hasEnded = false

    fun addEntry(logEntry: LogEntry) = entries.add(logEntry)
    fun getEntries(): List<LogEntry>
    {
        return entries.clone() as List<LogEntry>
    }
}