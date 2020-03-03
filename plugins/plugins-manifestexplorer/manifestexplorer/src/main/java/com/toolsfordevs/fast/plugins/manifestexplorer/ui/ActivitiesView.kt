package com.toolsfordevs.fast.plugins.manifestexplorer.ui

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.core.util.FlagUtil
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.recyclerview.ExpandableRenderer
import com.toolsfordevs.fast.modules.recyclerview.ExpandableRendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.decoration.Divider
import com.toolsfordevs.fast.plugins.manifestexplorer.ManifestInfoUtil


internal class ActivitiesView(context: Context) : FrameLayout(context)
{
    private val adapter = ExpandableRendererAdapter()

    init
    {
        adapter.addRenderer(ActivityInfo::class, ::ActivityRenderer, true)
        adapter.addRenderer(KeyValue::class, ::KeyValueRenderer)
        adapter.defaultExpanded = false

        val recyclerView = vRecyclerView(context)
        recyclerView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        recyclerView.itemAnimator = null
        recyclerView.addItemDecoration(Divider().apply {
            setDividerSizeDp(1)
            showLast = true
            setColor(MaterialColor.BLACK_12)
        })
        recyclerView.adapter = adapter

        val packageInfo = AppInstance.get().packageManager.getPackageInfo(AppInstance.get().packageName, PackageManager.GET_ACTIVITIES)
        packageInfo.activities?.filter { !it.name.startsWith("com.toolsfordevs.fast") }?.forEach { addActivity(it) }

        addView(recyclerView, layoutParamsMM())
    }

    @SuppressLint("NewApi")
    private fun addActivity(activityInfo: ActivityInfo)
    {
        adapter.add(activityInfo)

        val list = arrayListOf<KeyValue>()
        list.addAll(ManifestInfoUtil.getComponentInfoKeyValues(activityInfo))
        list.addAll(ManifestInfoUtil.getPackageItemInfoKeyValues(activityInfo))

        if (AndroidVersion.isMinOreo())
            list.add(KeyValue("colorMode", getColorMode(activityInfo.colorMode)))

        list.add(KeyValue("configChanges", getConfigChange(activityInfo.configChanges)))
        list.add(KeyValue("documentLaunchMode", getDocumentLaunchMode(activityInfo.documentLaunchMode)))
        list.add(KeyValue("flags", getFlags(activityInfo.flags)))
        list.add(KeyValue("launchMode", getLaunchMode(activityInfo.launchMode)))
        list.add(KeyValue("maxRecents", "${activityInfo.maxRecents}"))
        list.add(KeyValue("parentActivityName", activityInfo.parentActivityName))
        list.add(KeyValue("permission", activityInfo.permission))
        list.add(KeyValue("persistable", getPersistableMode(activityInfo.persistableMode)))
        list.add(KeyValue("screenOrientation", getScreenOrientation(activityInfo.screenOrientation)))
        list.add(KeyValue("softInputMode", getSoftInputMode(activityInfo.softInputMode)))
        list.add(KeyValue("target", activityInfo.targetActivity))
        list.add(KeyValue("taskAffinity", activityInfo.taskAffinity))
        list.add(KeyValue("theme", "${activityInfo.theme}"))
        list.add(KeyValue("uiOptions", getUIOptions(activityInfo.uiOptions)))

        if (AndroidVersion.isMinNougat())
            list.add(KeyValue("windowLayout", "${activityInfo.windowLayout}"))

        adapter.addAll(list.sortedBy { it.key })
    }

    private inner class ActivityRenderer(parent: ViewGroup) : ExpandableRenderer<ActivityInfo>(ManifestEntryView(parent.context)), OnClickListener
    {
        val view: ManifestEntryView = itemView as ManifestEntryView

        init
        {
            view.setOnClickListener(this)
            view.playButton.setOnClickListener(this)
        }

        override fun bind(data: ActivityInfo, position: Int)
        {
            view.name.text = data.name
        }

        override fun onClick(v: View?)
        {
            // On click on play, launch activity
            // on click on view, display activity info
            val item = getItem(adapterPosition) as ActivityInfo

            if (v == view)
                toggle()//item.dump({ data -> flog(data)}, "")
            else
                AppInstance.activity?.startActivity(Intent().apply { component = ComponentName(AppInstance.get().packageName, item.name) })
        }
    }

    @TargetApi(26)
    private fun getColorMode(mode: Int): String
    {
        return when (mode)
               {
                   ActivityInfo.COLOR_MODE_DEFAULT          -> "ActivityInfo.COLOR_MODE_DEFAULT"
                   ActivityInfo.COLOR_MODE_HDR              -> "ActivityInfo.COLOR_MODE_HDR"
                   ActivityInfo.COLOR_MODE_WIDE_COLOR_GAMUT -> "ActivityInfo.COLOR_MODE_WIDE_COLOR_GAMUT"
                   else                                     -> "?"
               } + " ($mode)"
    }

    private fun getDocumentLaunchMode(mode: Int): String
    {
        return when (mode)
               {
                   ActivityInfo.DOCUMENT_LAUNCH_ALWAYS        -> "ActivityInfo.DOCUMENT_LAUNCH_ALWAYS"
                   ActivityInfo.DOCUMENT_LAUNCH_INTO_EXISTING -> "ActivityInfo.DOCUMENT_LAUNCH_INTO_EXISTING"
                   ActivityInfo.DOCUMENT_LAUNCH_NEVER         -> "ActivityInfo.DOCUMENT_LAUNCH_NEVER"
                   ActivityInfo.DOCUMENT_LAUNCH_NONE          -> "ActivityInfo.DOCUMENT_LAUNCH_NONE"
                   else                                       -> "?"
               } + " ($mode)"
    }

    private fun getLaunchMode(mode: Int): String
    {
        return when (mode)
               {
                   ActivityInfo.LAUNCH_MULTIPLE        -> "ActivityInfo.LAUNCH_MULTIPLE"
                   ActivityInfo.LAUNCH_SINGLE_INSTANCE -> "ActivityInfo.LAUNCH_SINGLE_INSTANCE"
                   ActivityInfo.LAUNCH_SINGLE_TASK     -> "ActivityInfo.LAUNCH_SINGLE_TASK"
                   ActivityInfo.LAUNCH_SINGLE_TOP      -> "ActivityInfo.LAUNCH_SINGLE_TOP"
                   else                                -> "?"
               } + " ($mode)"
    }

    private fun getPersistableMode(mode: Int): String
    {
        return when (mode)
               {
                   ActivityInfo.PERSIST_ACROSS_REBOOTS -> "ActivityInfo.PERSIST_ACROSS_REBOOTS"
                   ActivityInfo.PERSIST_NEVER          -> "ActivityInfo.PERSIST_NEVER"
                   ActivityInfo.PERSIST_ROOT_ONLY      -> "ActivityInfo.PERSIST_ROOT_ONLY"
                   else                                -> "?"
               } + " ($mode)"
    }

    private fun getScreenOrientation(mode: Int): String
    {
        return when (mode)
               {
                   ActivityInfo.SCREEN_ORIENTATION_BEHIND            -> "ActivityInfo.SCREEN_ORIENTATION_BEHIND"
                   ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR       -> "ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR"
                   ActivityInfo.SCREEN_ORIENTATION_FULL_USER         -> "ActivityInfo.SCREEN_ORIENTATION_FULL_USER"
                   ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE         -> "ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE"
                   ActivityInfo.SCREEN_ORIENTATION_LOCKED            -> "ActivityInfo.SCREEN_ORIENTATION_LOCKED"
                   ActivityInfo.SCREEN_ORIENTATION_NOSENSOR          -> "ActivityInfo.SCREEN_ORIENTATION_NOSENSOR"
                   ActivityInfo.SCREEN_ORIENTATION_PORTRAIT          -> "ActivityInfo.SCREEN_ORIENTATION_PORTRAIT"
                   ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE -> "ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE"
                   ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT  -> "ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT"
                   ActivityInfo.SCREEN_ORIENTATION_SENSOR            -> "ActivityInfo.SCREEN_ORIENTATION_SENSOR"
                   ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE  -> "ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE"
                   ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT   -> "ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT"
                   ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED       -> "ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED"
                   ActivityInfo.SCREEN_ORIENTATION_USER              -> "ActivityInfo.SCREEN_ORIENTATION_USER"
                   ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE    -> "ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE"
                   ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT     -> "ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT"
                   else                                              -> "?"
               } + " ($mode)"
    }

    private fun getSoftInputMode(mode: Int): String
    {
        val map = hashMapOf(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING to "WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING",
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN to "WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN",
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE to "WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE",
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED to "WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED",
                WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION to "WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION",
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN to "WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN",
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE to "WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE",
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE to "WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE",
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN to "WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN",
                WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED to "WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED",
                WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED to "WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED",
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE to "WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE"
        )

        return FlagUtil.getFlagsIn(mode, map)
    }

    private fun getConfigChange(configChange: Int): String
    {
        val map = hashMapOf(
                ActivityInfo.CONFIG_DENSITY to "ActivityInfo.CONFIG_DENSITY",
                ActivityInfo.CONFIG_FONT_SCALE to "ActivityInfo.CONFIG_FONT_SCALE",
                ActivityInfo.CONFIG_KEYBOARD to "ActivityInfo.CONFIG_KEYBOARD",
                ActivityInfo.CONFIG_KEYBOARD_HIDDEN to "ActivityInfo.CONFIG_KEYBOARD_HIDDEN",
                ActivityInfo.CONFIG_LAYOUT_DIRECTION to "ActivityInfo.CONFIG_LAYOUT_DIRECTION",
                ActivityInfo.CONFIG_LOCALE to "ActivityInfo.CONFIG_LOCALE",
                ActivityInfo.CONFIG_MCC to "ActivityInfo.CONFIG_MCC",
                ActivityInfo.CONFIG_MNC to "ActivityInfo.CONFIG_MNC",
                ActivityInfo.CONFIG_NAVIGATION to "ActivityInfo.CONFIG_NAVIGATION",
                ActivityInfo.CONFIG_ORIENTATION to "ActivityInfo.CONFIG_ORIENTATION",
                ActivityInfo.CONFIG_SCREEN_LAYOUT to "ActivityInfo.CONFIG_SCREEN_LAYOUT",
                ActivityInfo.CONFIG_SCREEN_SIZE to "ActivityInfo.CONFIG_SCREEN_SIZE",
                ActivityInfo.CONFIG_SMALLEST_SCREEN_SIZE to "ActivityInfo.CONFIG_SMALLEST_SCREEN_SIZE",
                ActivityInfo.CONFIG_TOUCHSCREEN to "ActivityInfo.CONFIG_TOUCHSCREEN",
                ActivityInfo.CONFIG_UI_MODE to "ActivityInfo.CONFIG_UI_MODE"
        )

        if (AndroidVersion.isMinOreo())
            map[ActivityInfo.CONFIG_COLOR_MODE] = "ActivityInfo.CONFIG_COLOR_MODE"

        return FlagUtil.getFlagsIn(configChange, map)
    }

    private fun getFlags(flags: Int): String
    {
        val map = hashMapOf(
                ActivityInfo.FLAG_ALLOW_TASK_REPARENTING to "ActivityInfo.FLAG_ALLOW_TASK_REPARENTING",
                ActivityInfo.FLAG_ALWAYS_RETAIN_TASK_STATE to "ActivityInfo.FLAG_ALWAYS_RETAIN_TASK_STATE",
                ActivityInfo.FLAG_AUTO_REMOVE_FROM_RECENTS to "ActivityInfo.FLAG_AUTO_REMOVE_FROM_RECENTS",
                ActivityInfo.FLAG_CLEAR_TASK_ON_LAUNCH to "ActivityInfo.FLAG_CLEAR_TASK_ON_LAUNCH",
                ActivityInfo.FLAG_EXCLUDE_FROM_RECENTS to "ActivityInfo.FLAG_EXCLUDE_FROM_RECENTS",
                ActivityInfo.FLAG_FINISH_ON_CLOSE_SYSTEM_DIALOGS to "ActivityInfo.FLAG_FINISH_ON_CLOSE_SYSTEM_DIALOGS",
                ActivityInfo.FLAG_FINISH_ON_TASK_LAUNCH to "ActivityInfo.FLAG_FINISH_ON_TASK_LAUNCH",
                ActivityInfo.FLAG_HARDWARE_ACCELERATED to "ActivityInfo.FLAG_HARDWARE_ACCELERATED",
                ActivityInfo.FLAG_IMMERSIVE to "ActivityInfo.FLAG_IMMERSIVE",
                ActivityInfo.FLAG_MULTIPROCESS to "ActivityInfo.FLAG_MULTIPROCESS",
                ActivityInfo.FLAG_NO_HISTORY to "ActivityInfo.FLAG_NO_HISTORY",
                ActivityInfo.FLAG_RELINQUISH_TASK_IDENTITY to "ActivityInfo.FLAG_RELINQUISH_TASK_IDENTITY",
                ActivityInfo.FLAG_RESUME_WHILE_PAUSING to "ActivityInfo.FLAG_RESUME_WHILE_PAUSING",
                ActivityInfo.FLAG_SINGLE_USER to "ActivityInfo.FLAG_SINGLE_USER",
                ActivityInfo.FLAG_STATE_NOT_NEEDED to "ActivityInfo.FLAG_STATE_NOT_NEEDED"
        )

        if (AndroidVersion.isMinNougat())
            map[ActivityInfo.FLAG_ENABLE_VR_MODE] = "ActivityInfo.FLAG_ENABLE_VR_MODE"

        return FlagUtil.getFlagsIn(flags, map)
    }

    private fun getUIOptions(options:Int):String
    {
        return if (options == 0)
            "0"
        else if (options == ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW)
            return "ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW"
        else
            "$options"
    }
}