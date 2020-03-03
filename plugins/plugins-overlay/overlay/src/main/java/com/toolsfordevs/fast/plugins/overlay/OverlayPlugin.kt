package com.toolsfordevs.fast.plugins.overlay

import android.content.Context
import android.view.KeyEvent
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.annotation.FastIncludePlugin
import com.toolsfordevs.fast.plugins.overlay.ui.PluginContainer

@FastIncludePlugin
class OverlayPlugin : FastPlugin("com.toolsfordevs.fast.plugins.overlay", "Overlays", R.drawable.fast_plugin_overlay_plugin_icon)
{
    init
    {

    }

    override fun launch(context: Context)
    {
        FastManager.addView(PluginContainer(context))
    }

    override fun getDefaultHotkey(): Int
    {
        return KeyEvent.KEYCODE_O
    }
}