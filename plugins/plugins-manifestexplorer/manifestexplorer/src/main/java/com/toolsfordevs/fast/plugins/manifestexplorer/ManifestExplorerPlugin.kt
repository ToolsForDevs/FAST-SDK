package com.toolsfordevs.fast.plugins.manifestexplorer

import android.content.Context
import android.view.KeyEvent
import com.toolsfordevs.fast.plugins.manifestexplorer.ui.ManifestExplorerPanel
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.annotation.FastIncludePlugin

@FastIncludePlugin
class ManifestExplorerPlugin : FastPlugin("com.toolsfordevs.fast.plugins.manifestexplorer", "Manifest Explorer", R.drawable.fast_plugin_manifest_explorer_ic_plugin)
{
    override fun getDefaultHotkey(): Int
    {
        return KeyEvent.KEYCODE_M
    }

    override fun launch(context: Context)
    {
        Companion.launch(context)
    }

    companion object
    {
        fun launch(context: Context)
        {
            FastManager.addView(ManifestExplorerPanel(context))
        }
    }
}