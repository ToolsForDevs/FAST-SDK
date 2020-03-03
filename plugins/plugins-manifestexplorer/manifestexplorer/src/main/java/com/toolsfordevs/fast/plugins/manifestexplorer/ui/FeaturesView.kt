package com.toolsfordevs.fast.plugins.manifestexplorer.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.FeatureInfo
import android.content.pm.PackageManager
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.hide
import com.toolsfordevs.fast.core.util.AndroidVersion
import com.toolsfordevs.fast.core.util.FlagUtil
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.recyclerview.ExpandableRenderer
import com.toolsfordevs.fast.modules.recyclerview.ExpandableRendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.decoration.Divider


internal class FeaturesView(context: Context) : FrameLayout(context)
{
    private val adapter = ExpandableRendererAdapter()

    init
    {
        adapter.addRenderer(FeatureInfo::class, ::FeatureRenderer, true)
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

        val packageInfo = AppInstance.get().packageManager.getPackageInfo(AppInstance.get().packageName, PackageManager.GET_ACTIVITIES)
        // packageInfo.reqFeatures?.forEach { addFeature(it) }
        packageInfo.featureGroups?.toList()?.flatMap { it.features?.toList() ?: listOf<FeatureInfo>() }?.forEach { addFeature(it) }

        addView(recyclerView, layoutParamsMM())
    }

    @SuppressLint("NewApi")
    private fun addFeature(info: FeatureInfo)
    {
        adapter.add(info)

        val list = arrayListOf<KeyValue>()

        list.add(KeyValue("flags", getFlags(info.flags)))
        list.add(KeyValue("glEsVersion", info.glEsVersion))

        if (AndroidVersion.isMinNougat())
            list.add(KeyValue("version", "${info.version}"))

        adapter.addAll(list.sortedBy { it.key })
    }

    private fun getFlags(flags: Int): String
    {
        val map = hashMapOf(FeatureInfo.FLAG_REQUIRED to "FeatureInfo.FLAG_REQUIRED")

        return FlagUtil.getFlagsIn(flags, map)
    }

    private inner class FeatureRenderer(parent: ViewGroup) : ExpandableRenderer<FeatureInfo>(ManifestEntryView(parent.context)), OnClickListener
    {
        val view: ManifestEntryView = itemView as ManifestEntryView

        init
        {
            view.playButton.hide()
            view.setOnClickListener(this)
        }

        override fun bind(data: FeatureInfo, position: Int)
        {
            view.name.text = data.name
        }

        override fun onClick(v: View?)
        {
            toggle()
        }
    }
}