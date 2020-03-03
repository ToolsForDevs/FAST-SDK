package com.toolsfordevs.fast.plugins.crashinfo

import android.content.Context
import android.view.KeyEvent
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.annotation.FastIncludePlugin

@FastIncludePlugin
class CrashInfoPlugin : FastPlugin("com.toolsfordevs.fast.plugins.crashinfo", "CrashInfo", R.drawable.fast_plugin_crash_info_plugin_icon, false) {

    init {
        setup()
    }

    fun setup()
    {
        val handler = FastExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(handler)
        CrashRunnable.setUncaughtExceptionHandler(handler)
        CrashRunnable.install()
    }

    override fun launch(context: Context)
    {
        throw Exception("We can crash on demand, no problem!")
    }

    override fun getDefaultHotkey(): Int
    {
        return KeyEvent.KEYCODE_C
    }
}