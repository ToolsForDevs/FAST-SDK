package com.toolsfordevs.fast.plugins.actions.ui

import com.toolsfordevs.fast.core.prefs.BooleanPref
import com.toolsfordevs.fast.core.prefs.IntPref
import com.toolsfordevs.fast.core.prefs.JsonArrayPref
import com.toolsfordevs.fast.plugins.actions.ui.view.SortDelegate
import org.json.JSONArray


internal object Prefs
{
    private const val PLUGIN_ACTIONS_AUTO_FIRE_VALUE_CHANGES = "PLUGIN_ACTIONS_AUTO_FIRE_VALUE_CHANGES"
    private const val PLUGIN_ACTION_PIN_PANEL = "PLUGIN_ACTION_PIN_PANEL"
    private const val PLUGIN_ACTION_SELECTED_TAB = "PLUGIN_ACTION_SELECTED_TAB"

    var autoFireChanges:Boolean by BooleanPref(PLUGIN_ACTIONS_AUTO_FIRE_VALUE_CHANGES, false)
    var pinPanel:Boolean by BooleanPref(PLUGIN_ACTION_PIN_PANEL, false)
    var selectedTab:Int by IntPref(PLUGIN_ACTION_SELECTED_TAB, 0)


    private const val PLUGIN_ACTION_FAVORITES = "PLUGIN_ACTION_FAVORITES"
    var favoriteActions:JSONArray? by JsonArrayPref(PLUGIN_ACTION_FAVORITES)

    private const val PLUGIN_ACTION_SORT_TYPE = "PLUGIN_ACTION_SORT_TYPE"
    var sortType:Int by IntPref(PLUGIN_ACTION_SORT_TYPE, SortDelegate.LIFO)

    private const val PLUGIN_ACTION_FAVORITES_SORT_TYPE = "PLUGIN_ACTION_FAVORITES_SORT_TYPE"
    var favoriteSortType:Int by IntPref(PLUGIN_ACTION_FAVORITES_SORT_TYPE, SortDelegate.LIFO)
}