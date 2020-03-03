package com.toolsfordevs.fast.plugins.resourceexplorer

import android.content.Context
import android.view.KeyEvent
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.annotation.FastIncludePlugin
import com.toolsfordevs.fast.modules.resourcepicker.ResourceExplorerDialog
import com.toolsfordevs.fast.plugin.resourceexplorer.R

@FastIncludePlugin
class ResourceExplorerPlugin : FastPlugin("com.toolsfordevs.fast.plugins.resourceexplorer", "Resource Explorer", R.drawable.fast_plugin_resourceexplorer_ic_r)
{
    override fun getDefaultHotkey(): Int
    {
        return KeyEvent.KEYCODE_R
    }

    override fun launch(context: Context)
    {
        ResourceExplorerDialog(context).show()
    }
}