package com.toolsfordevs.fast

import android.content.Context
import android.view.KeyEvent
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.annotation.FastIncludePlugin

@FastIncludePlugin
internal class FastPluginLauncher : FastPlugin("com.toolsfordevs.fast.launcher", "FAST", 0, false)
{
    override fun getDefaultHotkey(): Int
    {
        return KeyEvent.KEYCODE_F
    }

    override fun launch(context: Context)
    {
        FastManager.addView(FastPluginToolbar(context))
    }
}