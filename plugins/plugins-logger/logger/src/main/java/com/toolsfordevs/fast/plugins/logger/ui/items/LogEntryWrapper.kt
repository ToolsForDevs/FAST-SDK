package com.toolsfordevs.fast.plugins.logger.ui.items

import com.toolsfordevs.fast.modules.formatters.FastFormatter
import com.toolsfordevs.fast.plugins.logger.model.LogEntry
import com.toolsfordevs.fast.plugins.logger.model.LogSession


internal data class LogEntryWrapper<T: FastFormatter>(val logEntry: LogEntry, val session: LogSession?, var formatter: T, val compatibleFormatters:List<FastFormatter>)
{
    var showMethodCall:Boolean = false
    var showStackTrace:Boolean = false
}