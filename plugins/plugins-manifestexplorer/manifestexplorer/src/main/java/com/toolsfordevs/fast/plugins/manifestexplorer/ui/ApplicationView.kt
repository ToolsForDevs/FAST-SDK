package com.toolsfordevs.fast.plugins.manifestexplorer.ui

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.view.View
import android.widget.FrameLayout
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.core.util.FlagUtil
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.decoration.Divider
import com.toolsfordevs.fast.plugins.manifestexplorer.ManifestInfoUtil


@SuppressLint("NewApi")
internal class ApplicationView(context: Context) : FrameLayout(context)
{
    private val adapter = RendererAdapter()

    init
    {
        adapter.addRenderer(KeyValue::class, ::KeyValueRenderer)

        val recyclerView = vRecyclerView(context)
        recyclerView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(Divider().apply {
            setDividerSizeDp(1)
            showLast = true
            setColor(MaterialColor.BLACK_12)
        })

        val info = AppInstance.get().applicationInfo

        val list = arrayListOf<KeyValue>()
        list.addAll(ManifestInfoUtil.getPackageItemInfoKeyValues(info))


        if (AndroidVersion.isMinPie())
            list.add(KeyValue("appComponentFactory", info.appComponentFactory))

        list.add(KeyValue("backupAgentName", info.backupAgentName))

        if (AndroidVersion.isMinOreo())
            list.add(KeyValue("category", getCategory(info.category)))

        list.add(KeyValue("className", info.className))
        list.add(KeyValue("compatibleWidthLimitDp", "${info.compatibleWidthLimitDp}"))
        list.add(KeyValue("dataDir", info.dataDir))
        list.add(KeyValue("descriptionRes", ResUtils.getStringResourceName(info.descriptionRes)))

        if (AndroidVersion.isMinNougat())
            list.add(KeyValue("deviceProtectedDataDir", info.deviceProtectedDataDir))

        list.add(KeyValue("enabled", "${info.enabled}"))
        list.add(KeyValue("flags", getFlags(info.flags)))
        list.add(KeyValue("largestWidthLimitDp", "${info.largestWidthLimitDp}"))
        list.add(KeyValue("manageSpaceActivityName", info.manageSpaceActivityName))

        if (AndroidVersion.isMinNougat())
            list.add(KeyValue("minSdkVersion", "${info.minSdkVersion}"))

        list.add(KeyValue("nativeLibraryDir", info.nativeLibraryDir))
        list.add(KeyValue("permission", info.permission))
        list.add(KeyValue("processName", info.processName))
        list.add(KeyValue("publicSourceDir", info.publicSourceDir))
        list.add(KeyValue("requiresSmallestWidthDp", "${info.requiresSmallestWidthDp}"))
        list.add(KeyValue("sharedLibraryFiles", info.sharedLibraryFiles?.toList()?.joinToString("\n")))
        list.add(KeyValue("sourceDir", info.sourceDir))

        if (AndroidVersion.isMinOreo())
            list.add(KeyValue("splitNames", info.splitNames?.toList()?.joinToString("\n")))

        list.add(KeyValue("splitPublicSourceDirs", info.splitPublicSourceDirs?.toList()?.joinToString("\n")))
        list.add(KeyValue("splitSourceDirs", info.splitSourceDirs?.toList()?.joinToString("\n")))

        if (AndroidVersion.isMinOreo())
            list.add(KeyValue("storageUuid", "${info.storageUuid}"))

        list.add(KeyValue("targetSdkVersion", "${info.targetSdkVersion}"))
        list.add(KeyValue("taskAffinity", info.taskAffinity))
        list.add(KeyValue("theme", ResUtils.getThemeResourceName(info.theme)))
        list.add(KeyValue("uiOptions", getUIOptions(info.uiOptions)))
        list.add(KeyValue("uid", "${info.uid}"))

        adapter.addAll(list.sortedBy { it.key })


        addView(recyclerView, layoutParamsMM())
    }

    @TargetApi(26)
    private fun getCategory(category: Int): String
    {
        return when (category)
               {
                   ApplicationInfo.CATEGORY_AUDIO        -> "ApplicationInfo.CATEGORY_AUDIO"
                   ApplicationInfo.CATEGORY_GAME         -> "ApplicationInfo.CATEGORY_GAME"
                   ApplicationInfo.CATEGORY_IMAGE        -> "ApplicationInfo.CATEGORY_IMAGE"
                   ApplicationInfo.CATEGORY_MAPS         -> "ApplicationInfo.CATEGORY_MAPS"
                   ApplicationInfo.CATEGORY_NEWS         -> "ApplicationInfo.CATEGORY_NEWS"
                   ApplicationInfo.CATEGORY_PRODUCTIVITY -> "ApplicationInfo.CATEGORY_PRODUCTIVITY"
                   ApplicationInfo.CATEGORY_SOCIAL       -> "ApplicationInfo.CATEGORY_SOCIAL"
                   ApplicationInfo.CATEGORY_UNDEFINED    -> "ApplicationInfo.CATEGORY_UNDEFINED"
                   ApplicationInfo.CATEGORY_VIDEO        -> "ApplicationInfo.CATEGORY_VIDEO"
                   else                                  -> "?"
               } + " ($category)"
    }

    private fun getFlags(flags: Int): String
    {
        val map = hashMapOf(
                ApplicationInfo.FLAG_ALLOW_BACKUP to "ActivityInfo.FLAG_ALLOW_BACKUP",
                ApplicationInfo.FLAG_ALLOW_CLEAR_USER_DATA to "ActivityInfo.FLAG_ALLOW_CLEAR_USER_DATA",
                ApplicationInfo.FLAG_ALLOW_TASK_REPARENTING to "ActivityInfo.FLAG_ALLOW_TASK_REPARENTING",
                ApplicationInfo.FLAG_DEBUGGABLE to "ActivityInfo.FLAG_DEBUGGABLE",
                ApplicationInfo.FLAG_EXTERNAL_STORAGE to "ActivityInfo.FLAG_EXTERNAL_STORAGE",
                ApplicationInfo.FLAG_EXTRACT_NATIVE_LIBS to "ActivityInfo.FLAG_EXTRACT_NATIVE_LIBS",
                ApplicationInfo.FLAG_FACTORY_TEST to "ActivityInfo.FLAG_FACTORY_TEST",
                ApplicationInfo.FLAG_FULL_BACKUP_ONLY to "ActivityInfo.FLAG_FULL_BACKUP_ONLY",
                ApplicationInfo.FLAG_HARDWARE_ACCELERATED to "ActivityInfo.FLAG_HARDWARE_ACCELERATED",
                ApplicationInfo.FLAG_HAS_CODE to "ActivityInfo.FLAG_HAS_CODE",
                ApplicationInfo.FLAG_INSTALLED to "ActivityInfo.FLAG_INSTALLED",
                ApplicationInfo.FLAG_IS_DATA_ONLY to "ActivityInfo.FLAG_IS_DATA_ONLY",
                ApplicationInfo.FLAG_KILL_AFTER_RESTORE to "ActivityInfo.FLAG_KILL_AFTER_RESTORE",
                ApplicationInfo.FLAG_LARGE_HEAP to "ActivityInfo.FLAG_LARGE_HEAP",
                ApplicationInfo.FLAG_MULTIARCH to "ActivityInfo.FLAG_MULTIARCH",
                ApplicationInfo.FLAG_PERSISTENT to "ActivityInfo.FLAG_PERSISTENT",
                ApplicationInfo.FLAG_RESIZEABLE_FOR_SCREENS to "ActivityInfo.FLAG_RESIZEABLE_FOR_SCREENS",
                ApplicationInfo.FLAG_RESTORE_ANY_VERSION to "ActivityInfo.FLAG_RESTORE_ANY_VERSION",
                ApplicationInfo.FLAG_STOPPED to "ActivityInfo.FLAG_STOPPED",
                ApplicationInfo.FLAG_SUPPORTS_LARGE_SCREENS to "ActivityInfo.FLAG_SUPPORTS_LARGE_SCREENS",
                ApplicationInfo.FLAG_SUPPORTS_NORMAL_SCREENS to "ActivityInfo.FLAG_SUPPORTS_NORMAL_SCREENS",
                ApplicationInfo.FLAG_SUPPORTS_RTL to "ActivityInfo.FLAG_SUPPORTS_RTL",
                ApplicationInfo.FLAG_SUPPORTS_SCREEN_DENSITIES to "ActivityInfo.FLAG_SUPPORTS_SCREEN_DENSITIES",
                ApplicationInfo.FLAG_SUPPORTS_SMALL_SCREENS to "ActivityInfo.FLAG_SUPPORTS_SMALL_SCREENS",
                ApplicationInfo.FLAG_SUPPORTS_XLARGE_SCREENS to "ActivityInfo.FLAG_SUPPORTS_XLARGE_SCREENS",
                ApplicationInfo.FLAG_SUSPENDED to "ActivityInfo.FLAG_SUSPENDED",
                ApplicationInfo.FLAG_SYSTEM to "ActivityInfo.FLAG_SYSTEM",
                ApplicationInfo.FLAG_TEST_ONLY to "ActivityInfo.FLAG_TEST_ONLY",
                ApplicationInfo.FLAG_UPDATED_SYSTEM_APP to "ActivityInfo.FLAG_UPDATED_SYSTEM_APP",
                ApplicationInfo.FLAG_USES_CLEARTEXT_TRAFFIC to "ActivityInfo.FLAG_USES_CLEARTEXT_TRAFFIC",
                ApplicationInfo.FLAG_VM_SAFE_MODE to "ActivityInfo.FLAG_VM_SAFE_MODE"
        )

        if (AndroidVersion.isMinNougat())
            map[ActivityInfo.FLAG_ENABLE_VR_MODE] = "ActivityInfo.FLAG_ENABLE_VR_MODE"

        return FlagUtil.getFlagsIn(flags, map)
    }

    private fun getUIOptions(options:Int):String
    {
        return when (options)
        {
            0                                                  -> "0"
            ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW -> return "ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW"
            else                                               -> "$options"
        }
    }
}