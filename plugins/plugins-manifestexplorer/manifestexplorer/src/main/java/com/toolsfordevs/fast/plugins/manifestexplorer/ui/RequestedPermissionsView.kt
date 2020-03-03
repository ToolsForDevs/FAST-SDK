package com.toolsfordevs.fast.plugins.manifestexplorer.ui

import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.toolsfordevs.fast.core.AppInstance
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.hide
import com.toolsfordevs.fast.modules.androidx.recyclerview.ext.vRecyclerView
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.ViewRenderer
import com.toolsfordevs.fast.modules.recyclerview.decoration.Divider


internal class RequestedPermissionsView(context: Context) : FrameLayout(context)
{
    private val adapter = RendererAdapter()

    init
    {
        adapter.addRenderer(String::class, ::PermissionRenderer)

        val recyclerView = vRecyclerView(context)
        recyclerView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        recyclerView.addItemDecoration(Divider().apply {
            setDividerSizeDp(1)
            showLast = true
            setColor(MaterialColor.BLACK_12)
        })
        recyclerView.adapter = adapter

        val packageInfo = AppInstance.get().packageManager.getPackageInfo(AppInstance.get().packageName, PackageManager.GET_PERMISSIONS)
        adapter.addAll(packageInfo.requestedPermissions?.toList() ?: listOf<String>())

        addView(recyclerView, layoutParamsMM())
    }

    private inner class PermissionRenderer(parent: ViewGroup) : ViewRenderer<String, ManifestEntryView>(ManifestEntryView(parent.context))
    {
        init
        {
            view.playButton.hide()
        }

        override fun bind(data: String, position: Int)
        {
            view.name.text = data
        }
    }
}