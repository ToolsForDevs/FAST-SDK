package com.toolsfordevs.fast.plugins.crashinfo.ui

import com.toolsfordevs.fast.core.prefs.BooleanPref


internal object Prefs
{
    private const val PLUGIN_CRASHINFO_SHOW_FILENAME = "PLUGIN_CRASHINFO_SHOW_FILENAME"
    private const val PLUGIN_CRASHINFO_FULL_CLASSNAME = "PLUGIN_CRASHINFO_FULL_CLASSNAME"

    var showFilename:Boolean by BooleanPref(PLUGIN_CRASHINFO_SHOW_FILENAME, false)
    var showFullClassname:Boolean by BooleanPref(PLUGIN_CRASHINFO_FULL_CLASSNAME, false)
}