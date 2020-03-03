package com.toolsfordevs.fast.plugins.logger

import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.plugins.logger.model.LogEntry
import com.toolsfordevs.fast.modules.formatters.FastFormatter
import com.toolsfordevs.fast.plugins.logger.model.Priority

private fun flog(message:String, tag:String? = null, priority: Priority = Priority.DEFAULT)
{
    Store.addLog(LogEntry(message, tag, priority, displayAsRawString = true))
}

private fun flog(data:Any?, tag:String? = null, formatter: FastFormatter? = null, priority: Priority = Priority.DEFAULT)
{
    Store.addLog(LogEntry(data, tag, priority, formatter))
}

private fun flogInspect(data:Any?, tag:String? = null, priority: Priority = Priority.DEFAULT)
{
    Store.addLog(LogEntry(data, tag, priority, introspectionReport = true))
}

private fun flogRaw(data:Any?, tag:String? = null, priority: Priority = Priority.DEFAULT)
{
    Store.addLog(LogEntry(data, tag, priority, displayAsRawString = true))
}

// ############################################################################
// #
// # Public API
// #
// ############################################################################
@Keep fun flogStartSession(name:String = "", color:Int = FastColor.colorAccent)
{
    Store.startSession(name, color)
}
@Keep fun flogStopSession()
{
    Store.stopSession()
}
@Keep fun flog(message:String, tag:String? = null)
{
    Store.addLog(LogEntry(message, tag, Priority.DEFAULT, displayAsRawString = true))
}
@Keep fun flog(data:Any?, tag:String? = null, formatter: FastFormatter? = null)
{
    Store.addLog(LogEntry(data, tag, Priority.DEFAULT, formatter))
}
@Keep fun flog(vararg data: Any?, startNewSession:Boolean = false, sessionName:String = "")
{
    if (startNewSession)
        flogStartSession(sessionName)

    for (datum in data)
        flog(datum)

    if (startNewSession)
        flogStopSession()
}
@Keep fun flogInspect(data:Any?, tag:String? = null)
{
    Store.addLog(LogEntry(data, tag, Priority.DEFAULT, introspectionReport = true))
}
@Keep fun flogRaw(data:Any?, tag:String? = null)
{
    Store.addLog(LogEntry(data, tag, Priority.DEFAULT, displayAsRawString = true))
}

@Keep fun flogI(message:String, tag:String? = null) = flog(message, tag, Priority.INFO)
@Keep fun flogI(data:Any?, tag:String? = null, formatter: FastFormatter? = null) = flog(data, tag, formatter, Priority.INFO)
@Keep fun flogInspectI(data:Any?, tag:String? = null) = flogInspect(data, tag, Priority.INFO)
@Keep fun flogRawI(data:Any?, tag:String? = null) = flogRaw(data, tag, Priority.INFO)

@Keep fun flogD(message:String, tag:String? = null) = flog(message, tag, Priority.DEBUG)
@Keep fun flogD(data:Any?, tag:String? = null, formatter: FastFormatter? = null) = flog(data, tag, formatter, Priority.DEBUG)
@Keep fun flogInspectD(data:Any?, tag:String? = null) = flogInspect(data, tag, Priority.DEBUG)
@Keep fun flogRawD(data:Any?, tag:String? = null) = flogRaw(data, tag, Priority.DEBUG)

@Keep fun flogW(message:String, tag:String? = null) = flog(message, tag, Priority.WARNING)
@Keep fun flogW(data:Any?, tag:String? = null, formatter: FastFormatter? = null) = flog(data, tag, formatter, Priority.WARNING)
@Keep fun flogInspectW(data:Any?, tag:String? = null) = flogInspect(data, tag, Priority.WARNING)
@Keep fun flogRawW(data:Any?, tag:String? = null) = flogRaw(data, tag, Priority.WARNING)

@Keep fun flogE(message:String, tag:String? = null) = flog(message, tag, Priority.ERROR)
@Keep fun flogE(data:Any?, tag:String? = null, formatter: FastFormatter? = null) = flog(data, tag, formatter, Priority.ERROR)
@Keep fun flogInspectE(data:Any?, tag:String? = null) = flogInspect(data, tag, Priority.ERROR)
@Keep fun flogRawE(data:Any?, tag:String? = null) = flogRaw(data, tag, Priority.ERROR)

@Keep fun flogWTF(message:String, tag:String? = null) = flog(message, tag, Priority.WTF)
@Keep fun flogWTF(data:Any?, tag:String? = null, formatter: FastFormatter? = null) = flog(data, tag, formatter, Priority.WTF)
@Keep fun flogInspectWTF(data:Any?, tag:String? = null) = flogInspect(data, tag, Priority.WTF)
@Keep fun flogRawWTF(data:Any?, tag:String? = null) = flogRaw(data, tag, Priority.WTF)