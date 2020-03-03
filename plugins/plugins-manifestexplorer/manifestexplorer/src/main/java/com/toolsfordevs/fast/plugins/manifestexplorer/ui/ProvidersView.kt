package com.toolsfordevs.fast.plugins.manifestexplorer.ui

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ProviderInfo
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.hide
import com.toolsfordevs.fast.core.util.FlagUtil
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.recyclerview.ExpandableRenderer
import com.toolsfordevs.fast.modules.recyclerview.ExpandableRendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.decoration.Divider
import com.toolsfordevs.fast.plugins.manifestexplorer.ManifestInfoUtil


internal class ProvidersView(context: Context) : FrameLayout(context)
{
    private val adapter = ExpandableRendererAdapter()

    init
    {
        adapter.addRenderer(ProviderInfo::class, ::ProviderRenderer, true)
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

        val packageInfo = AppInstance.get().packageManager.getPackageInfo(AppInstance.get().packageName, PackageManager.GET_PROVIDERS)
        packageInfo.providers?.filter { !it.name.startsWith("com.toolsfordevs.fast") }?.forEach { addProvider(it) }

        addView(recyclerView, layoutParamsMM())
    }

    private fun addProvider(providerInfo: ProviderInfo)
    {
        adapter.add(providerInfo)

        val list = arrayListOf<KeyValue>()
        list.addAll(ManifestInfoUtil.getComponentInfoKeyValues(providerInfo))
        list.addAll(ManifestInfoUtil.getPackageItemInfoKeyValues(providerInfo))

        list.add(KeyValue("authority", providerInfo.authority))
        list.add(KeyValue("flags", getFlags(providerInfo.flags)))

        // if (AndroidVersion.isMinAndroid10())
        //     list.add(KeyValue("forceUriPermissions", "${providerInfo.forceUriPermissions}"))

        list.add(KeyValue("grantUriPermissions", "${providerInfo.grantUriPermissions}"))
        list.add(KeyValue("initOrder", "${providerInfo.initOrder}"))
        list.add(KeyValue("multiprocess", "${providerInfo.multiprocess}"))
        list.add(KeyValue("pathPermissions", providerInfo.pathPermissions?.toList()?.joinToString("\n")))
        list.add(KeyValue("readPermission", providerInfo.readPermission))
        list.add(KeyValue("uriPermissionPatterns", providerInfo.uriPermissionPatterns?.toList()?.joinToString("\n")))
        list.add(KeyValue("writePermission", providerInfo.writePermission))

        adapter.addAll(list.sortedBy { it.key })
    }

    private fun getFlags(flags: Int): String
    {
        val map = hashMapOf(ProviderInfo.FLAG_SINGLE_USER to "ProviderInfo.FLAG_SINGLE_USER")

        return FlagUtil.getFlagsIn(flags, map)
    }

    private inner class ProviderRenderer(parent: ViewGroup) : ExpandableRenderer<ProviderInfo>(ManifestEntryView(parent.context)), OnClickListener
    {
        val view: ManifestEntryView = itemView as ManifestEntryView

        init
        {
            view.playButton.hide()
            view.setOnClickListener(this)
        }

        override fun bind(data: ProviderInfo, position: Int)
        {
            view.name.text = data.name
        }

        override fun onClick(v: View?)
        {
            toggle()
        }
    }
}