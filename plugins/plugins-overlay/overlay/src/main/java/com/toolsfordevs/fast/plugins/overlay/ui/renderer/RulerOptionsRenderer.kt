package com.toolsfordevs.fast.plugins.overlay.ui.renderer

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.toolsfordevs.fast.plugins.overlay.ui.model.RulerOptions
import com.toolsfordevs.fast.plugins.overlay.ui.widget.RulerPreview

internal class RulerOptionsRenderer(parent: ViewGroup) : BaseOptionsRenderer<RulerOptions, RulerOptionView>(
    RulerOptionView(parent.context))
{
    override fun bind(data: RulerOptions, position: Int)
    {
        super.bind(data, position)

        view.rulerPreview.setColor(data::color, data::strokeStyle)

        bindColor(data::color, view.color)

        // We don't want "NONE" in the list for Ruler
        bindSpinner(listOf("SOLID", "DASHED", "DOTS"), data::strokeStyle, view.strokeStyle)
        //bindStrokeStyle(data.strokeStyle, view.strokeStyle)
    }
}

internal class RulerOptionView(context: Context) : BaseOptionsView(context)
{
    val rulerPreview = preview as RulerPreview
    val color = makeColorButton()
    val strokeStyle = makeSpinner()

    init
    {
        addItem("Color", color)
        addItem("Stroke style", strokeStyle)
    }

    override fun makePreview(): View
    {
        return RulerPreview(context)
    }
}