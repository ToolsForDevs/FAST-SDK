package com.toolsfordevs.fast.plugins.overlay.ui.view

import android.content.Context
import com.toolsfordevs.fast.core.ext.layoutParamsMM
import com.toolsfordevs.fast.core.ui.ext.setPaddingVertical
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.FastRecyclerView
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.LinearLayoutManager
import com.toolsfordevs.fast.modules.recyclerview.RendererAdapter
import com.toolsfordevs.fast.modules.recyclerview.decoration.Divider
import com.toolsfordevs.fast.plugins.overlay.ui.Prefs
import com.toolsfordevs.fast.plugins.overlay.ui.renderer.*

internal class ViewOptionsView(context: Context) : FastRecyclerView(context)
{
    init
    {
        layoutParams = layoutParamsMM()
        setBackgroundColor(0xFFDDDDDD.toInt())

        layoutManager = LinearLayoutManager(context)
        setHasFixedSize(true)

        addItemDecoration(Divider().apply { setDividerSizeDp(16) })

        setPaddingVertical(Dimens.dp(16))
        clipToPadding = false

        val adapter = RendererAdapter()
        adapter.addRenderer(::HierarchyBoundOptionsRenderer)
        adapter.addRenderer(::BoundOptionsRenderer)
        adapter.addRenderer(::RulerOptionsRenderer)
        adapter.addRenderer(::SizeOptionsRenderer)
        adapter.addRenderer(::PositionOptionsRenderer)
        adapter.addRenderer(::MarginOptionsRenderer)
        adapter.addRenderer(::MarginDimensionsOptionsRenderer)
        adapter.addRenderer(::PaddingOptionsRenderer)
        adapter.addRenderer(::PaddingDimensionsOptionsRenderer)
        adapter.mode = RendererAdapter.MODE_INSTANCE_OF

        val profile = Prefs.currentProfile

        adapter.add(profile.hierarchyOptions)
        adapter.add(profile.boundsOptions)
        adapter.add(profile.rulerOptions)
        adapter.add(profile.sizeOptions)
        adapter.add(profile.positionOptions)
        adapter.add(profile.marginOptions)
        adapter.add(profile.marginDimensionsOptions)
        adapter.add(profile.paddingOptions)
        adapter.add(profile.paddingDimensionsOptions)

        this.adapter = adapter
    }
}