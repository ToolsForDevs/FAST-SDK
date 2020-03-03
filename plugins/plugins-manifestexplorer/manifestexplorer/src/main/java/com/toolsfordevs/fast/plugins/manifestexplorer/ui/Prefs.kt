package com.toolsfordevs.fast.plugins.manifestexplorer.ui

import com.toolsfordevs.fast.core.prefs.IntPref

internal object Prefs
{
    var selectedTab by IntPref("PLUGIN_MANIFEST_EXPLORER_SELECTED_TAB", 0)
}