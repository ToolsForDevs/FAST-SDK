package com.toolsfordevs.fast.plugins.fileexplorer

import android.content.Context
import android.view.KeyEvent
import com.toolsfordevs.fast.core.FastManager
import com.toolsfordevs.fast.core.FastPlugin
import com.toolsfordevs.fast.core.annotation.FastIncludePlugin
import com.toolsfordevs.fast.plugins.fileexplorer.ui.FileExplorerPanel

@FastIncludePlugin
class FileExplorerPlugin : FastPlugin("com.toolsfordevs.fast.plugins.fileexplorer", "File Explorer", R.drawable.fast_plugin_fileexplorer_ic_plugin)
{
    override fun getDefaultHotkey(): Int
    {
        return KeyEvent.KEYCODE_G
    }

    override fun launch(context: Context)
    {
        FileExplorerPlugin.launch(context)
    }

    companion object
    {
        fun launch(context: Context)
        {
            FastManager.addView(FileExplorerPanel(context))
        }
    }
}