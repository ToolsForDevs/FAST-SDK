package com.fast.toolsfordevs.plugins.newplugin

import android.content.Context
import android.view.KeyEvent
import com.fast.toolsfordevs.plugins.newplugin.ui.WindowFlagsPanel
import com.fast.toolsfordevs.plugins.windowflags.R
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.annotation.FastIncludePlugin

@FastIncludePlugin
class WindowFlagsPlugin : FastPlugin("com.toolsfordevs.fast.plugins.windowflags", "Window Flags", R.drawable.fast_plugin_window_flags_ic_plugin)
{
    override fun getDefaultHotkey(): Int
    {
        return KeyEvent.KEYCODE_W
    }

    override fun launch(context: Context)
    {
        WindowFlagsPlugin.launch(context)
    }

    companion object
    {
        fun launch(context: Context)
        {
            FastManager.addView(WindowFlagsPanel(context))
        }
    }
}