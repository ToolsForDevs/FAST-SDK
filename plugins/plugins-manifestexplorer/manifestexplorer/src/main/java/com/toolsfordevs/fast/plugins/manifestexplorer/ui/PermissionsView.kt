package com.toolsfordevs.fast.plugins.manifestexplorer.ui

import android.content.Context
import android.content.pm.ConfigurationInfo
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.hide
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.core.util.FlagUtil
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.recyclerview.ExpandableRenderer
import com.toolsfordevs.fast.modules.recyclerview.ExpandableRendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.decoration.Divider
import com.toolsfordevs.fast.plugins.manifestexplorer.ManifestInfoUtil


internal class PermissionsView(context: Context) : FrameLayout(context)
{
    private val adapter = ExpandableRendererAdapter()

    init
    {
        adapter.addRenderer(ConfigurationInfo::class, ::PermissionRenderer, true)
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

        val packageInfo = AppInstance.get().packageManager.getPackageInfo(AppInstance.get().packageName, PackageManager.GET_PERMISSIONS)
        packageInfo.permissions?.forEach { addService(it) }

        addView(recyclerView, layoutParamsMM())
    }

    private fun addService(info: PermissionInfo)
    {
        adapter.add(info)

        val list = arrayListOf<KeyValue>()

        list.addAll(ManifestInfoUtil.getPackageItemInfoKeyValues(info))

        list.add(KeyValue("description", ResUtils.getStringResourceName(info.descriptionRes)))
        list.add(KeyValue("group", info.group))
        list.add(KeyValue("flags", getFlags(info.flags)))
        list.add(KeyValue("nonLocalizedDescription", info.nonLocalizedDescription?.toString()))

        adapter.addAll(list.sortedBy { it.key })
    }

    private fun getFlags(flags: Int): String
    {
        val map = hashMapOf(
                PermissionInfo.FLAG_COSTS_MONEY to "ConfigurationInfo.FLAG_COSTS_MONEY",
                PermissionInfo.FLAG_HARD_RESTRICTED to "ConfigurationInfo.FLAG_HARD_RESTRICTED",
                PermissionInfo.FLAG_IMMUTABLY_RESTRICTED to "ConfigurationInfo.FLAG_IMMUTABLY_RESTRICTED",
                PermissionInfo.FLAG_SOFT_RESTRICTED to "ConfigurationInfo.FLAG_SOFT_RESTRICTED"
        )

        if (AndroidVersion.isMinMarshmallow())
            map[PermissionInfo.FLAG_INSTALLED] = "ConfigurationInfo.FLAG_INSTALLED"

        return FlagUtil.getFlagsIn(flags, map)
    }

    private inner class PermissionRenderer(parent: ViewGroup) : ExpandableRenderer<PermissionInfo>(ManifestEntryView(parent.context)), OnClickListener
    {
        val view: ManifestEntryView = itemView as ManifestEntryView

        init
        {
            view.playButton.hide()
            view.setOnClickListener(this)
        }

        override fun bind(data: PermissionInfo, position: Int)
        {
            view.name.text = "Permission $position"
        }

        override fun onClick(v: View?)
        {
            toggle()
        }
    }
}