package com.toolsfordevs.fast.plugins.sharedprefs

import com.toolsfordevs.fast.core.prefs.StringPref
import com.toolsfordevs.fast.core.util.FastSort

internal object Prefs
{
    var lastSelectedPreferenceFile: String? by StringPref("PLUGIN_SHARED_PREF_LAST_SELECTED_FILE", null)
    var mainSorting: String? by StringPref("PLUGIN_SHARED_PREF_LAST_MAIN_SORT", FastSort.DEFAULT)
}