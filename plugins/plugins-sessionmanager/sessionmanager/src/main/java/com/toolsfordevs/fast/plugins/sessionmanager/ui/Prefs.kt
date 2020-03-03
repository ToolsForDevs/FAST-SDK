package com.toolsfordevs.fast.plugins.sessionmanager.ui

import com.toolsfordevs.fast.core.prefs.IntPref
import com.toolsfordevs.fast.core.util.FastSort
import com.toolsfordevs.fast.plugins.sessionmanager.prefs.SortTypePrefs

internal object Prefs
{
    var selectedTab by IntPref("PLUGIN_SESSION_MANAGER_SELECTED_TAB", 0)
    private var tabSortTypes by SortTypePrefs()

    fun getTabSortType(category:String):String
    {
        return tabSortTypes[category] ?: FastSort.DEFAULT
    }

    fun setTabSortType(category:String, sortType:String)
    {
        tabSortTypes[category] = sortType
        tabSortTypes = tabSortTypes // Save
    }
}