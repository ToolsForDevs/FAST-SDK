package com.toolsfordevs.fast.plugins.manifestexplorer

import android.annotation.SuppressLint
import android.content.pm.ComponentInfo
import android.content.pm.PackageItemInfo
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.plugins.manifestexplorer.ui.KeyValue

internal object ManifestInfoUtil
{
    @SuppressLint("NewApi")
    fun getComponentInfoKeyValues(info: ComponentInfo): List<KeyValue>
    {
        val list = arrayListOf(
                KeyValue("descriptionRes", ResUtils.getStringResourceName(info.descriptionRes)),
                KeyValue("enabled", "${info.enabled}"),
                KeyValue("exported", "${info.exported}"),
                KeyValue("processName", info.processName)
        )

        if (AndroidVersion.isMinNougat())
            list.add(KeyValue("directBootAware", "${info.directBootAware}"))

        if (AndroidVersion.isMinOreo())
            list.add(KeyValue("splitName", info.splitName))

        return list
    }

    @SuppressLint("NewApi")
    fun getPackageItemInfoKeyValues(info: PackageItemInfo): List<KeyValue>
    {
        return listOf(
                KeyValue("banner", ResUtils.getDrawableResourceName(info.banner)),
                KeyValue("icon", ResUtils.getDrawableResourceName(info.icon)),
                KeyValue("labelRes", ResUtils.getStringResourceName(info.labelRes)),
                KeyValue("logo", ResUtils.getDrawableResourceName(info.logo)),
                KeyValue("metaData", info.metaData?.toString()),
                KeyValue("name", info.name),
                KeyValue("nonLocalizedLabel", info.nonLocalizedLabel?.toString()),
                KeyValue("packageName", info.packageName)
        )
    }
}