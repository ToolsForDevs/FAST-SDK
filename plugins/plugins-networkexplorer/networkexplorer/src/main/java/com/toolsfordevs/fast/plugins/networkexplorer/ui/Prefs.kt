package com.toolsfordevs.fast.plugins.networkexplorer.ui

import com.toolsfordevs.fast.core.prefs.IntPref

internal object Prefs
{
    var selectedTab:Int by IntPref("NETWORK_EXPLORER_REQUEST_SELECTED_TAB", 0)
}