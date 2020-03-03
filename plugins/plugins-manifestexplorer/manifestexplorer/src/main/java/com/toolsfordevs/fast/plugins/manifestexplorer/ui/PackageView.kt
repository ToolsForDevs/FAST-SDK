package com.toolsfordevs.fast.plugins.manifestexplorer.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.view.View
import android.widget.FrameLayout
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.decoration.Divider
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("NewApi")
internal class PackageView(context: Context) : FrameLayout(context)
{
    private val adapter = RendererAdapter()

    init
    {
        adapter.addRenderer(KeyValue::class, ::KeyValueRenderer)

        val recyclerView = vRecyclerView(context)
        recyclerView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        recyclerView.itemAnimator = null
        recyclerView.addItemDecoration(Divider().apply {
            setDividerSizeDp(1)
            showLast = true
            setColor(MaterialColor.BLACK_12)
        })
        recyclerView.adapter = adapter

        val packageInfo = AppInstance.get().packageManager.getPackageInfo(AppInstance.get().packageName, PackageManager.GET_GIDS)

        if (AndroidVersion.isMinLollipopMR1())
            adapter.add(KeyValue("baseRevisionCode", "${packageInfo.baseRevisionCode}"))

        adapter.add(KeyValue("firstInstallTime", getDate(packageInfo.firstInstallTime)))
        adapter.add(KeyValue("gids", getInts(packageInfo.gids)))
        adapter.add(KeyValue("installLocation", getInstallLocation(packageInfo.installLocation)))
        adapter.add(KeyValue("lastUpdateTime", getDate(packageInfo.lastUpdateTime)))
        adapter.add(KeyValue("packageName", packageInfo.packageName))
        adapter.add(KeyValue("sharedUserId", packageInfo.sharedUserId))
        adapter.add(KeyValue("sharedUserLabel", "${packageInfo.sharedUserLabel}"))
        adapter.add(KeyValue("splitNames", packageInfo.splitNames?.joinToString("\n") ?: ""))

        if (AndroidVersion.isMinLollipopMR1())
            adapter.add(KeyValue("splitRevisionCodes", getInts(packageInfo.splitRevisionCodes)))

        if (AndroidVersion.isMinPie())
            adapter.add(KeyValue("versionCode", "${packageInfo.longVersionCode}"))
        else
            adapter.add(KeyValue("versionCode", "${packageInfo.versionCode}"))

        adapter.add(KeyValue("versionName", packageInfo.versionName))

        addView(recyclerView, layoutParamsMM())
    }

    private fun getDate(timestamp: Long): String
    {
        if (timestamp == 0L)
            return "0"

        val df = SimpleDateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG)
        return df.format(Date(timestamp))
    }

    private fun getInts(array: IntArray?): String
    {
        return array?.joinToString("\n") ?: ""
    }

    private fun getInstallLocation(location: Int): String
    {
        return when (location)
        {
            PackageInfo.INSTALL_LOCATION_AUTO            -> "PackageInfo.INSTALL_LOCATION_AUTO"
            PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY   -> "PackageInfo.INSTALL_LOCATION_INTERNAL_ONLY"
            PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL -> "PackageInfo.INSTALL_LOCATION_PREFER_EXTERNAL"
            else                                         -> "?"
        } + " ($location)"
    }
}