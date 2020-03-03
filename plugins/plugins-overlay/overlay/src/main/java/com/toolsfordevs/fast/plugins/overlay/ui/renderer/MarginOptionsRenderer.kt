package com.toolsfordevs.fast.plugins.overlay.ui.renderer

import android.content.Context
import android.view.ViewGroup
import com.toolsfordevs.fast.plugins.overlay.ui.model.MarginOptions


internal class MarginOptionsRenderer(parent: ViewGroup) : BaseOptionsRenderer<MarginOptions, MarginOptionsView>(
    MarginOptionsView(parent.context))
{
    override fun bind(data: MarginOptions, position: Int)
    {
        super.bind(data, position)
        view.colorPreview.setColors(data::backgroundColor, data::borderColor, data::strokeStyle)
        bindColor(data::backgroundColor, view.bgdColor)
        bindColor(data::borderColor, view.borderColor)
        bindBackgroundStyle(data::backgroundStyle, view.backgroundStyle)
        bindStrokeStyle(data::strokeStyle, view.strokeStyle)
    }
}

internal class MarginOptionsView(context: Context) : BackgroundOptionView(context)
{
    val backgroundStyle = makeSpinner()
    val strokeStyle = makeSpinner()

    init {
        addItem("Background style", backgroundStyle)
        addItem("Stroke style", strokeStyle)
    }
}