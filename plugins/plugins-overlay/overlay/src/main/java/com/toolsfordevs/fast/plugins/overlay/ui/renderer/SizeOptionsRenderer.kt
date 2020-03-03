package com.toolsfordevs.fast.plugins.overlay.ui.renderer

import android.content.Context
import android.view.ViewGroup
import com.toolsfordevs.fast.plugins.overlay.ui.model.ViewSizeOptions


internal class SizeOptionsRenderer(parent: ViewGroup) : BaseOptionsRenderer<ViewSizeOptions, SizeOptionsView>(
    SizeOptionsView(parent.context))
{
    override fun bind(data: ViewSizeOptions, position: Int)
    {
        super.bind(data, position)

        view.colorPreview.text = "S"
        view.colorPreview.setColors(data::backgroundColor, data::borderColor, null, data::textColor)

        bindColor(data::backgroundColor, view.bgdColor)
        bindColor(data::borderColor, view.borderColor)
        bindColor(data::textColor, view.textColor)
        bindUnit(data::unit, view.unit)
        bindBoolean(data::showUnit, view.showUnit)
        bindBoolean(data::roundedCorners, view.roundedCorners)
    }
}

internal class SizeOptionsView(context: Context) : BackgroundOptionView(context)
{
    val textColor = makeColorButton()
    val unit = makeSpinner()
    val showUnit = makeSwitch()
    val roundedCorners = makeSwitch()

    init {
        addItem("Text color", textColor)
        addItem("Unit", unit)
        addItem("Show unit", showUnit)
        addItem("Rounded corners", roundedCorners)
    }
}