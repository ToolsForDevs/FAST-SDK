package com.toolsfordevs.fast.plugins.fileexplorer.ui

import com.toolsfordevs.fast.core.prefs.StringPref
import com.toolsfordevs.fast.core.util.FastSort

internal object Prefs
{
    var lastSelectedFolder:String? by StringPref("PLUGIN_FILE_EXPLORER_LAST_SELECTED_FOLDER", null)
    var lastFolder:String? by StringPref("PLUGIN_FILE_EXPLORER_LAST_FOLDER", null)
    var sortType:String? by StringPref("PLUGIN_FILE_EXPLORER_SORT_TYPE", FastSort.ALPHA_ASC)
}