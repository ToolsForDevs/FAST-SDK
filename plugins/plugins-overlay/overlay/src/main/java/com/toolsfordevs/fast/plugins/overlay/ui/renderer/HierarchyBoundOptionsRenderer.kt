package com.toolsfordevs.fast.plugins.overlay.ui.renderer

import android.content.Context
import android.view.ViewGroup
import com.toolsfordevs.fast.plugins.overlay.ui.model.HierarchyBoundOptions


internal class HierarchyBoundOptionsRenderer(parent: ViewGroup) : BaseOptionsRenderer<HierarchyBoundOptions, HierarchyOptionsView>(
    HierarchyOptionsView(parent.context))
{
    override fun bind(data: HierarchyBoundOptions, position: Int)
    {
        super.bind(data, position)
        view.colorPreview.setColors(data::backgroundColor, data::borderColor, data::strokeStyle)
        bindColor(data::backgroundColor, view.bgdColor)
        bindColor(data::borderColor, view.borderColor)
        bindBackgroundStyle(data::backgroundStyle, view.backgroundStyle)
        bindStrokeStyle(data::strokeStyle, view.strokeStyle)
        bindBoolean(data::showCorners, view.showCorners)
        bindBoolean(data::excludeViewGroups, view.excludeViewGroups)
        bindBoolean(data::scrim, view.scrim)
        bindColor(data::scrimColor, view.scrimColor)
    }
}

internal class HierarchyOptionsView(context: Context) : BoundOptionsView(context)
{
    val excludeViewGroups = makeSwitch()
    val scrim = makeSwitch()
    val scrimColor = makeColorButton()

    init {
        addItem("Exclude ViewGroups", excludeViewGroups)
        addItem("Scrim", scrim)
        addItem("Scrim Color", scrimColor)
    }
}