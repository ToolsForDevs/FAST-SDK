package com.toolsfordevs.fast.plugins.viewinspector

import android.content.Context
import android.view.KeyEvent
import android.widget.Toast
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.annotation.FastIncludePlugin
import com.toolsfordevs.fast.plugins.viewinspector.ui.PluginContainer
import com.toolsfordevs.fast.plugins.viewinspector.ui.ViewInspectorModel

@FastIncludePlugin
class ViewInspectorPlugin : FastPlugin("com.toolsfordevs.fast.plugins.viewinspector", "View Inspector", R.drawable.fast_plugin_viewinspector_plugin_icon)
{
    init
    {
    }
    override fun launch(context: Context)
    {
        ViewInspectorModel.checkIds()
        ViewInspectorModel.checkNames()
        FastManager.addView(PluginContainer(context))

        Toast.makeText(context, "Select a view", Toast.LENGTH_SHORT).show()
    }

    override fun getDefaultHotkey(): Int
    {
        return KeyEvent.KEYCODE_V
    }
}