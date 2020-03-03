package com.toolsfordevs.fast.plugins.manifestexplorer.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.view.View
import android.view.ViewGroup
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


internal class ServicesView(context: Context) : FrameLayout(context)
{
    private val adapter = ExpandableRendererAdapter()

    init
    {
        adapter.addRenderer(ServiceInfo::class, ::ServiceRenderer, true)
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

        val packageInfo = AppInstance.get().packageManager.getPackageInfo(AppInstance.get().packageName, PackageManager.GET_SERVICES)
        packageInfo.services?.filter { !it.name.startsWith("com.toolsfordevs.fast") }?.forEach { addService(it) }

        addView(recyclerView, layoutParamsMM())
    }

    private fun addService(serviceInfo: ServiceInfo)
    {
        adapter.add(serviceInfo)

        val list = arrayListOf<KeyValue>()
        list.addAll(ManifestInfoUtil.getComponentInfoKeyValues(serviceInfo))
        list.addAll(ManifestInfoUtil.getPackageItemInfoKeyValues(serviceInfo))

        list.add(KeyValue("flags", getFlags(serviceInfo.flags)))
        list.add(KeyValue("permission", serviceInfo.permission))

        adapter.addAll(list.sortedBy { it.key })
    }

    private fun getFlags(flags: Int): String
    {
        val map = hashMapOf(
                ServiceInfo.FLAG_ISOLATED_PROCESS to "ActivityInfo.FLAG_ISOLATED_PROCESS",
                ServiceInfo.FLAG_SINGLE_USER to "ActivityInfo.FLAG_SINGLE_USER",
                ServiceInfo.FLAG_STOP_WITH_TASK to "ActivityInfo.FLAG_STOP_WITH_TASK",
                ServiceInfo.FLAG_USE_APP_ZYGOTE to "ActivityInfo.FLAG_USE_APP_ZYGOTE"
        )

        if (AndroidVersion.isMinNougat())
            map[ServiceInfo.FLAG_EXTERNAL_SERVICE] = "ActivityInfo.FLAG_EXTERNAL_SERVICE"

        return FlagUtil.getFlagsIn(flags, map)
    }


    private inner class ServiceRenderer(parent: ViewGroup) : ExpandableRenderer<ServiceInfo>(ManifestEntryView(parent.context)), OnClickListener
    {
        val view: ManifestEntryView = itemView as ManifestEntryView

        init
        {
            view.setOnClickListener(this)
            view.playButton.setOnClickListener(this)
        }

        override fun bind(data: ServiceInfo, position: Int)
        {
            view.name.text = data.name
        }

        override fun onClick(v: View?)
        {
            // On click on play, launch activity
            // on click on view, display activity info
            val item = getItem(adapterPosition) as ServiceInfo

            if (v == view)
                toggle()
            else
                AppInstance.activity?.startService(Intent().apply { component = ComponentName(AppInstance.get ().packageName, item.name) })
        }
    }
}