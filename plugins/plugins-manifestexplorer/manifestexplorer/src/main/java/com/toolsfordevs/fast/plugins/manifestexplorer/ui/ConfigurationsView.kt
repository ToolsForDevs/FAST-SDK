package com.toolsfordevs.fast.plugins.manifestexplorer.ui

import android.content.Context
import android.content.pm.ConfigurationInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
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


internal class ConfigurationsView(context: Context) : FrameLayout(context)
{
    private val adapter = ExpandableRendererAdapter()

    init
    {
        adapter.addRenderer(ConfigurationInfo::class, ::ConfigurationRenderer, true)
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

        val packageInfo = AppInstance.get().packageManager.getPackageInfo(AppInstance.get().packageName, PackageManager.GET_CONFIGURATIONS)
        packageInfo.configPreferences?.forEach { addService(it) }

        addView(recyclerView, layoutParamsMM())
    }

    private fun addService(info: ConfigurationInfo)
    {
        adapter.add(info)

        val list = arrayListOf<KeyValue>()

        list.add(KeyValue("glEsVersion", info.glEsVersion))
        list.add(KeyValue("reqInputFeatures", getInputFeatures(info.reqInputFeatures)))
        list.add(KeyValue("reqKeyboardType", getKeyboardType(info.reqKeyboardType)))
        list.add(KeyValue("reqNavigation", getNavigation(info.reqNavigation)))
        list.add(KeyValue("reqTouchScreen", getTouchscreen(info.reqTouchScreen)))

        adapter.addAll(list.sortedBy { it.key })
    }

    private fun getInputFeatures(flags: Int): String
    {
        val map = hashMapOf(
                ConfigurationInfo.INPUT_FEATURE_FIVE_WAY_NAV to "ConfigurationInfo.INPUT_FEATURE_FIVE_WAY_NAV",
                ConfigurationInfo.INPUT_FEATURE_HARD_KEYBOARD to "ConfigurationInfo.INPUT_FEATURE_HARD_KEYBOARD"
        )

        return FlagUtil.getFlagsIn(flags, map)
    }

    private fun getKeyboardType(type: Int): String
    {
        return when (type)
        {
            Configuration.KEYBOARD_12KEY -> "Configuration.KEYBOARD_12KEY"
            Configuration.KEYBOARD_NOKEYS -> "Configuration.KEYBOARD_NOKEYS"
            Configuration.KEYBOARD_QWERTY -> "Configuration.KEYBOARD_QWERTY"
            Configuration.KEYBOARD_UNDEFINED -> "Configuration.KEYBOARD_UNDEFINED"
            else -> "?"
        }
    }

    private fun getNavigation(type: Int): String
    {
        return when (type)
        {
            Configuration.NAVIGATION_DPAD -> "Configuration.NAVIGATION_DPAD"
            Configuration.NAVIGATION_TRACKBALL -> "Configuration.NAVIGATION_TRACKBALL"
            Configuration.NAVIGATION_WHEEL -> "Configuration.NAVIGATION_WHEEL"
            Configuration.NAVIGATION_UNDEFINED -> "Configuration.NAVIGATION_UNDEFINED"
            else -> "?"
        }
    }

    private fun getTouchscreen(type: Int): String
    {
        return when (type)
        {
            Configuration.TOUCHSCREEN_FINGER -> "Configuration.TOUCHSCREEN_FINGER"
            Configuration.TOUCHSCREEN_NOTOUCH -> "Configuration.TOUCHSCREEN_NOTOUCH"
            Configuration.TOUCHSCREEN_UNDEFINED -> "Configuration.TOUCHSCREEN_UNDEFINED"
            else -> "?"
        }
    }


    private inner class ConfigurationRenderer(parent: ViewGroup) : ExpandableRenderer<ConfigurationInfo>(ManifestEntryView(parent.context)), OnClickListener
    {
        val view: ManifestEntryView = itemView as ManifestEntryView

        init
        {
            view.playButton.hide()
            view.setOnClickListener(this)
        }

        override fun bind(data: ConfigurationInfo, position: Int)
        {
            view.name.text = "Configuration $position"
        }

        override fun onClick(v: View?)
        {
            toggle()
        }
    }
}