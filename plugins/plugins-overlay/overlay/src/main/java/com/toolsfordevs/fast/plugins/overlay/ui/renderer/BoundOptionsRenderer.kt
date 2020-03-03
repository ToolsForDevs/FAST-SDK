package com.toolsfordevs.fast.plugins.overlay.ui.renderer

import android.content.Context
import android.view.ViewGroup
import com.toolsfordevs.fast.plugins.overlay.ui.model.ViewBoundOptions
import com.toolsfordevs.fast.plugins.overlay.ui.widget.BackgroundPreview


internal class BoundOptionsRenderer(parent: ViewGroup) : BaseOptionsRenderer<ViewBoundOptions, BoundOptionsView>(
    BoundOptionsView(parent.context))
{
    override fun bind(data: ViewBoundOptions, position: Int)
    {
        super.bind(data, position)
        view.colorPreview.setColors(data::backgroundColor, data::borderColor, data::strokeStyle)
        bindColor(data::backgroundColor, view.bgdColor)
        bindColor(data::borderColor, view.borderColor)
        bindBackgroundStyle(data::backgroundStyle, view.backgroundStyle)
        bindStrokeStyle(data::strokeStyle, view.strokeStyle)
        bindBoolean(data::showCorners, view.showCorners)
    }
}

internal open class BackgroundOptionView(context: Context) : BaseOptionsView(context)
{
    val colorPreview = preview as BackgroundPreview
    val bgdColor = makeColorButton()
    val borderColor= makeColorButton()

    init
    {
        addItem("Background color", bgdColor)
        addItem("Border color", borderColor)
    }
}

internal open class BoundOptionsView(context: Context) : BackgroundOptionView(context)
{
    val backgroundStyle = makeSpinner()
    val strokeStyle = makeSpinner()
    val showCorners = makeSwitch()

    init {
        addItem("Background style", backgroundStyle)
        addItem("Stroke style", strokeStyle)
        addItem("Show corners", showCorners)
    }
}