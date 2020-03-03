package com.toolsfordevs.fast.plugins.logger

import android.content.Context
import com.toolsfordevs.fast.core.annotation.Keep

@Keep
class LoggerPlugin
{
    companion object
    {
        fun launch(context: Context)
        {
            // No-op
        }

        fun setColorInfo(color:Int)
        {
            // No-op
        }

        fun setColorDebug(color:Int)
        {
            // No-op
        }

        fun setColorWarning(color:Int)
        {
            // No-op
        }

        fun setColorError(color:Int)
        {
            // No-op
        }

        fun setColorWTF(color:Int)
        {
            // No-op
        }

        fun setEnableSendToLogcat(enabled:Boolean)
        {
            // No-op
        }

        fun excludeClassesFromStacktrace(vararg classnames:String)
        {
            // No-op
        }

        fun excludePackagesFromStacktrace(vararg packages:String)
        {
            // No-op
        }
    }
}