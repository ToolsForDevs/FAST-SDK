package com.fast.toolsfordevs.modules.codegenerator.ui

import com.toolsfordevs.fast.core.prefs.StringPref

internal object Prefs
{
    var lastSelectedLanguage:String? by StringPref("MODE_CODE_SNIPPET_LAST_SELECTED_LANGUAGE", null)
}