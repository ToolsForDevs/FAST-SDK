package com.toolsfordevs.fast.plugins.logger

import android.graphics.Color
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.formatters.FastFormatter


@Keep
fun flog(message:String, tag:String? = null, color:Int = Color.TRANSPARENT)
{
    // No-op
}

@Keep fun flog(data:Any?, tag:String? = null, formatter: FastFormatter? = null, color:Int = Color.TRANSPARENT)
{
    // No-op
}

@Keep fun flog(vararg data: Any?)
{
    // No-op
}

@Keep fun flogInspect(data:Any?, tag:String? = null, color:Int = Color.TRANSPARENT)
{
    // No-op
}

@Keep fun flogRaw(data:Any?, tag:String? = null, color:Int = Color.TRANSPARENT)
{
    // No-op
}

@Keep fun flogStartSession(name:String = "", color:Int = defColor)
{
    // No-op
}

@Keep fun flogStopSession()
{
    // No-op
}

// ############################################################################
// #
// # Colored version
// #
// ############################################################################

val defColor = 0xFF00000000.toInt()

@Keep fun flogI(message:String, tag:String? = null) = flog(message, tag, defColor)
@Keep fun flogI(data:Any?, tag:String? = null, formatter: FastFormatter? = null) = flog(data, tag, formatter, defColor)
@Keep fun flogInspectI(data:Any?, tag:String? = null) = flogInspect(data, tag, defColor)
@Keep fun flogRawI(data:Any?, tag:String? = null) = flogRaw(data, tag, defColor)

@Keep fun flogD(message:String, tag:String? = null) = flog(message, tag, defColor)
@Keep fun flogD(data:Any?, tag:String? = null, formatter: FastFormatter? = null) = flog(data, tag, formatter, defColor)
@Keep fun flogInspectD(data:Any?, tag:String? = null) = flogInspect(data, tag, defColor)
@Keep fun flogRawD(data:Any?, tag:String? = null) = flogRaw(data, tag, defColor)

@Keep fun flogW(message:String, tag:String? = null) = flog(message, tag, defColor)
@Keep fun flogW(data:Any?, tag:String? = null, formatter: FastFormatter? = null) = flog(data, tag, formatter, defColor)
@Keep fun flogInspectW(data:Any?, tag:String? = null) = flogInspect(data, tag, defColor)
@Keep fun flogRawW(data:Any?, tag:String? = null) = flogRaw(data, tag, defColor)

@Keep fun flogE(message:String, tag:String? = null) = flog(message, tag, defColor)
@Keep fun flogE(data:Any?, tag:String? = null, formatter: FastFormatter? = null) = flog(data, tag, formatter, defColor)
@Keep fun flogInspectE(data:Any?, tag:String? = null) = flogInspect(data, tag, defColor)
@Keep fun flogRawE(data:Any?, tag:String? = null) = flogRaw(data, tag, defColor)

@Keep fun flogWTF(message:String, tag:String? = null) = flog(message, tag, defColor)
@Keep fun flogWTF(data:Any?, tag:String? = null, formatter: FastFormatter? = null) = flog(data, tag, formatter, defColor)
@Keep fun flogInspectWTF(data:Any?, tag:String? = null) = flogInspect(data, tag, defColor)
@Keep fun flogRawWTF(data:Any?, tag:String? = null) = flogRaw(data, tag, defColor)