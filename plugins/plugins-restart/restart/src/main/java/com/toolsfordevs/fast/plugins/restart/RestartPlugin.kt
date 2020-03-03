package com.toolsfordevs.fast.plugins.restart

import android.content.Context
import android.view.KeyEvent
import com.toolsfoldevs.fast.plugins.restart.R
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.annotation.FastIncludePlugin
import com.toolsfordevs.fast.modules.restart.FastRestartPanel

@FastIncludePlugin
class RestartPlugin : FastPlugin("com.toolsfordevs.fast.plugins.restart", "Restart", R.drawable.fast_plugin_restart_ic_plugin
)
{
    override fun getDefaultHotkey(): Int
    {
        return KeyEvent.KEYCODE_Q
    }

    override fun launch(context: Context)
    {
        Companion.launch(context)
    }

    companion object
    {
        fun launch(context: Context)
        {
            FastManager.addView(FastRestartPanel(context))
        }
    }
}