package com.toolsfordevs.fast.plugins.sharedprefs

import android.content.Context
import android.view.KeyEvent
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.annotation.FastIncludePlugin
import com.toolsfordevs.fast.plugins.sharedprefs.ui.SharedPrefsPanel

@FastIncludePlugin
class SharedPrefsPlugin : FastPlugin(
        "com.toolsfordevs.fast.plugins.sharedprefs",
        "Shared Preferences",
        R.drawable.fast_plugin_shared_preferences_plugin_icon, true
)
{
    override fun getDefaultHotkey(): Int
    {
        return KeyEvent.KEYCODE_S
    }

    override fun launch(context: Context)
    {
        SharedPrefsPlugin.launch(context)
    }

    companion object
    {
        fun launch(context:Context)
        {
            FastManager.addView(SharedPrefsPanel(context))
        }
    }
}