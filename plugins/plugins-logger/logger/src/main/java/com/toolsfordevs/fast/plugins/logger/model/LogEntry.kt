package com.toolsfordevs.fast.plugins.logger.model

import com.toolsfordevs.fast.modules.formatters.FastFormatter
import java.lang.RuntimeException


internal data class LogEntry(val data:Any? = null, val tag:String? = null, val priority: Priority = Priority.DEFAULT, val formatter: FastFormatter? = null, val displayAsRawString:Boolean = false, val introspectionReport:Boolean = false)
{
    var timestamp:Long = System.currentTimeMillis()
    var stackTrace:Array<StackTraceElement>

    init {

        var s:Array<StackTraceElement> = arrayOf()

        try {
            throw RuntimeException("forced log exception")
        }
        catch (e: Exception)
        {
            s = e.stackTrace
        }

        stackTrace = s
    }
}