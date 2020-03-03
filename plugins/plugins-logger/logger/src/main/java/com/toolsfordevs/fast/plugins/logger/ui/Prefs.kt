package com.toolsfordevs.fast.plugins.logger.ui

import com.toolsfordevs.fast.core.prefs.BooleanPref
import com.toolsfordevs.fast.core.prefs.IntPref
import com.toolsfordevs.fast.core.prefs.StringArrayPref
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.plugins.logger.ui.prefs.FilterProfile
import com.toolsfordevs.fast.plugins.logger.ui.prefs.FilterProfileArrayPref
import java.util.*


internal object Prefs
{
    private const val PLUGIN_LOGGER_PIN_PANEL = "PLUGIN_LOGGER_PIN_PANEL"
    private const val PLUGIN_LOGGER_PANEL_OPACITY = "PLUGIN_LOGGER_PANEL_OPACITY"
    private const val PLUGIN_LOGGER_PANEL_SCROLL_TO_END = "PLUGIN_LOGGER_PANEL_SCROLL_TO_END"
    private const val PLUGIN_LOGGER_PANEL_SHOW_TIME = "PLUGIN_LOGGER_PANEL_SHOW_TIME"
    private const val PLUGIN_LOGGER_PANEL_SHOW_TAG = "PLUGIN_LOGGER_PANEL_SHOW_TAG"

    var pinPanel: Boolean by BooleanPref(PLUGIN_LOGGER_PIN_PANEL, false)
    var panelOpacity: Boolean by BooleanPref(PLUGIN_LOGGER_PANEL_OPACITY, false)
    var scrollToEnd: Boolean by BooleanPref(PLUGIN_LOGGER_PANEL_SCROLL_TO_END, false)
    var showTime: Boolean by BooleanPref(PLUGIN_LOGGER_PANEL_SHOW_TIME, true)
    var showTag: Boolean by BooleanPref(PLUGIN_LOGGER_PANEL_SHOW_TAG, true)

    private const val PLUGIN_LOGGER_COLOR_DEFAULT = "PLUGIN_LOGGER_COLOR_DEFAULT"
    private const val PLUGIN_LOGGER_COLOR_INFO = "PLUGIN_LOGGER_COLOR_INFO"
    private const val PLUGIN_LOGGER_COLOR_DEBUG = "PLUGIN_LOGGER_COLOR_DEBUG"
    private const val PLUGIN_LOGGER_COLOR_WARNING = "PLUGIN_LOGGER_COLOR_WARNING"
    private const val PLUGIN_LOGGER_COLOR_ERROR = "PLUGIN_LOGGER_COLOR_ERROR"
    private const val PLUGIN_LOGGER_COLOR_WTF = "PLUGIN_LOGGER_COLOR_WTF"

    var colorDefault: Int by IntPref(PLUGIN_LOGGER_COLOR_DEFAULT, MaterialColor.BLACK_12) //0xff03A9F4.toInt())
    var colorInfo: Int by IntPref(PLUGIN_LOGGER_COLOR_INFO, MaterialColor.BLUE_500) //0xff03A9F4.toInt())
    var colorDebug: Int by IntPref(PLUGIN_LOGGER_COLOR_DEBUG, MaterialColor.LIGHTGREEN_500) //0xff00FF00.toInt())
    var colorWarning: Int by IntPref(PLUGIN_LOGGER_COLOR_WARNING, MaterialColor.ORANGE_500) //0xffF57C00.toInt())
    var colorError: Int by IntPref(PLUGIN_LOGGER_COLOR_ERROR, MaterialColor.RED_500) //0xffFF0000.toInt())
    var colorWTF: Int by IntPref(PLUGIN_LOGGER_COLOR_WTF, MaterialColor.BLACK_100) //0xffffff00.toInt())

    private const val PLUGIN_LOGGER_SEND_TO_LOGGER = "PLUGIN_LOGGER_SEND_TO_LOGGER"
    var sendToLogcat: Boolean by BooleanPref(PLUGIN_LOGGER_SEND_TO_LOGGER, false)

    private const val PLUGIN_LOGGER_MAX_ENTRIES = "PLUGIN_LOGGER_MAX_ENTRIES"
    var maxEntries: Int by IntPref(PLUGIN_LOGGER_MAX_ENTRIES, 100)

    /* We don't want to save the excluded classes as one might want to remove
    * some at some point.
    * For example, user sets up some excluded classes in code somewhere, they get saved in prefs (sharedprefs)
    * Then user deleted the code and don't understand why he can't see its classes in the stacktrace anymore
     */

    val excludedStacktraceClasses = TreeSet<String>()
    val excludedStacktracePackages = TreeSet<String>()

    private const val PLUGIN_LOGGER_FILTER_SELECTED_TAB = "PLUGIN_LOGGER_FILTER_SELECTED_TAB"
    var filterSelectedTab: Int by IntPref(PLUGIN_LOGGER_FILTER_SELECTED_TAB, 0)

    var filterProfiles: List<FilterProfile> by FilterProfileArrayPref()

    private const val PLUGIN_LOGGER_FILTER_CURRENT_PROFILE_IDS = "PLUGIN_LOGGER_FILTER_CURRENT_PROFILE_IDS"
    private var currentProfileIds: List<String> by StringArrayPref(PLUGIN_LOGGER_FILTER_CURRENT_PROFILE_IDS)

    var currentProfiles: List<FilterProfile> = listOf()
        get()
        {
            val ids = currentProfileIds
            return filterProfiles.filter { ids.contains(it.id) }
        }
        set(value)
        {
            field = value
            currentProfileIds = value.map { it.id }
        }

    fun removeProfile(profile:FilterProfile)
    {
        val profiles = filterProfiles.toMutableList()

        profiles.indexOfFirst { it.id == profile.id }.takeIf { it > -1 }?.also {
            profiles.removeAt(it)
            filterProfiles = profiles
        }

        val ids = currentProfileIds.toMutableList()

        ids.indexOfFirst { it == profile.id }.takeIf { it > -1 }?.also {
            ids.removeAt(it)
            currentProfileIds = ids
        }
    }

    fun saveProfile(profile:FilterProfile)
    {
        val profiles = filterProfiles.toMutableList()

        val index = profiles.indexOfFirst { it.id == profile.id }

        if (index >= 0)
        {
            profiles.removeAt(index)
            profiles.add(index, profile)
        }
        else
        {
            profiles.add(profile)
        }

        filterProfiles = profiles
    }

    fun setAsCurrentProfile(profile:FilterProfile, current:Boolean)
    {
        val ids = currentProfileIds.toMutableList()

        if (current)
        {
            if (!ids.contains(profile.id))
                ids.add(profile.id)
        }
        else
        {
            ids.remove(profile.id)
        }

        currentProfileIds = ids
    }

    fun isCurrentProfile(profile:FilterProfile):Boolean
    {
        return currentProfileIds.contains(profile.id)
    }

}