package com.toolsfordevs.fast.plugins.overlay.ui

import com.toolsfordevs.fast.core.prefs.BooleanPref
import com.toolsfordevs.fast.core.prefs.FastPrefObjectArrayPref
import com.toolsfordevs.fast.core.prefs.IntPref
import com.toolsfordevs.fast.core.prefs.StringPref
import com.toolsfordevs.fast.plugins.overlay.ui.model.OverlayProfile

internal object Prefs
{
    // ToDo Save profile configurations instead too many key/pair values

    var selectedTab: Int by IntPref("PLUGIN_OVERLAY_SELECTED_TAB", 0)
    var lockedMode by BooleanPref("PLUGIN_OVERLAY_LOCKED_MODE", true)

    var profiles by FastPrefObjectArrayPref("PLUGIN_OVERLAY_PROFILES", ::OverlayProfile, listOf(
        OverlayProfile.default()))
    var currentProfileId by StringPref("PLUGIN_OVERLAY_CURRENT_PROFILE_ID", OverlayProfile.DEFAULT_ID)

    val currentProfile: OverlayProfile by lazy { profiles
        .first { it.id == currentProfileId } }

    fun saveCurrentProfile()
    {
        profiles = profiles
    }
}