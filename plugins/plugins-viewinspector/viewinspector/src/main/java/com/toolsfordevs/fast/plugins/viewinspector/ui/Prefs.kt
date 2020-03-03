package com.toolsfordevs.fast.plugins.viewinspector.ui

import com.toolsfordevs.fast.core.prefs.BooleanPref
import com.toolsfordevs.fast.core.prefs.IntPref
import com.toolsfordevs.fast.core.prefs.StringPref
import com.toolsfordevs.fast.core.widget.FastPanel


internal object Prefs
{
    private const val PLUGIN_VIEWINSPECTOR_MODE = "PLUGIN_VIEWINSPECTOR_MODE"
    private const val PLUGIN_VIEWINSPECTOR_USE_PX = "PLUGIN_VIEWINSPECTOR_USE_PX"
    private const val PLUGIN_VIEWINSPECTOR_SHOW_HEADER = "PLUGIN_VIEWINSPECTOR_SHOW_HEADER"
    private const val PLUGIN_VIEWINSPECTOR_VIEWPAGER_INDEX = "PLUGIN_VIEWINSPECTOR_VIEWPAGER_INDEX"
    private const val PLUGIN_VIEWINSPECTOR_PANEL_OPACITY = "PLUGIN_VIEWINSPECTOR_PANEL_OPACITY"

    private const val PLUGIN_VIEWINSPECTOR_FAVORITE_PROPERTIES = "PLUGIN_VIEWINSPECTOR_FAVORITE_PROPERTIES"


    var mode:Int by IntPref(PLUGIN_VIEWINSPECTOR_MODE, FastPanel.MODE_ON_TOP_OF)
    var usePx:Boolean by BooleanPref(PLUGIN_VIEWINSPECTOR_USE_PX, false)
    var showHeader:Boolean by BooleanPref(PLUGIN_VIEWINSPECTOR_SHOW_HEADER, false)
    var viewPagerIndex:Int by IntPref(PLUGIN_VIEWINSPECTOR_VIEWPAGER_INDEX, 0)
    var panelOpacity:Boolean by BooleanPref(PLUGIN_VIEWINSPECTOR_PANEL_OPACITY, false)

    var favoriteProperties:String? by StringPref(PLUGIN_VIEWINSPECTOR_FAVORITE_PROPERTIES, "{}")
}