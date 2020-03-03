package com.toolsfordevs.fast.plugins.logger

import android.content.Context
import android.view.KeyEvent
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.annotation.FastIncludePlugin
import com.toolsfordevs.fast.plugins.logger.ui.Panel
import com.toolsfordevs.fast.plugins.logger.ui.Prefs

@FastIncludePlugin
class LoggerPlugin : FastPlugin("com.toolsfordevs.fast.plugins.logger", "Logger", R.drawable.fast_plugin_logcat_plugin_icon)
{
    init
    {
        excludePackagesFromStacktrace("com.toolsfordevs.fast.plugins.logger")
    }

    override fun launch(context: Context)
    {
        LoggerPlugin.launch(context)
    }

    override fun getDefaultHotkey(): Int
    {
        return KeyEvent.KEYCODE_L
    }

    companion object
    {
        fun launch(context:Context)
        {
            FastManager.addView(Panel(context))
        }

        fun setColorInfo(color:Int)
        {
            Prefs.colorInfo = color
        }

        fun setColorDebug(color:Int)
        {
            Prefs.colorDebug = color
        }

        fun setColorWarning(color:Int)
        {
            Prefs.colorWarning = color
        }

        fun setColorError(color:Int)
        {
            Prefs.colorError = color
        }

        fun setColorWTF(color:Int)
        {
            Prefs.colorWTF = color
        }

        fun setEnableSendToLogcat(enabled:Boolean)
        {
            Prefs.sendToLogcat = enabled
        }

        fun excludeClassesFromStacktrace(vararg classnames:String)
        {
            for (classname in classnames)
                Prefs.excludedStacktraceClasses.add(classname)
        }

        fun excludePackagesFromStacktrace(vararg packages:String)
        {
            for (p in packages)
                Prefs.excludedStacktracePackages.add(p)
        }
    }
}