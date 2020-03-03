package com.toolsfordevs.fast.plugins.networkexplorer

import android.content.Context
import android.view.KeyEvent
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.annotation.FastIncludePlugin
import com.toolsfordevs.fast.plugins.networkexplorer.ui.ListPanel

@FastIncludePlugin
class NetworkExplorerPlugin : FastPlugin("com.toolsfordevs.fast.plugins.networkexplorer", "Network Explorer", R.drawable.fast_networkexplorer_plugin_icon)
{
    override fun getDefaultHotkey(): Int
    {
        return KeyEvent.KEYCODE_N
    }

    override fun launch(context: Context)
    {
        NetworkExplorerPlugin.launch(context)
    }

    companion object
    {
        fun launch(context: Context)
        {
            FastManager.addView(ListPanel(context))
        }
    }
}