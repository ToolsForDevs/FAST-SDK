package com.toolsfordevs.fast.plugins.overlay.ui.renderer

import android.content.Context
import android.view.ViewGroup
import com.toolsfordevs.fast.plugins.overlay.ui.model.ViewPositionOptions


internal class PositionOptionsRenderer(parent: ViewGroup) : BaseOptionsRenderer<ViewPositionOptions, PositionOptionsView>(
    PositionOptionsView(parent.context))
{
    override fun bind(data: ViewPositionOptions, position: Int)
    {
        super.bind(data, position)

        view.colorPreview.text = data.text
        view.colorPreview.setColors(data::backgroundColor, data::borderColor, null, data::textColor)

        bindColor(data::backgroundColor, view.bgdColor)
        bindColor(data::borderColor, view.borderColor)
        bindColor(data::textColor, view.textColor)
        bindUnit(data::unit, view.unit)
        bindBoolean(data::showUnit, view.showUnit)
        bindBoolean(data::roundedCorners, view.roundedCorners)
        bindSpinner(listOf("PARENT", "CONTENT VIEW", "SCREEN"), data::relativeTo, view.relativeTo)
    }
}

internal class PositionOptionsView(context: Context) : BackgroundOptionView(context)
{
    val textColor = makeColorButton()
    val unit = makeSpinner()
    val showUnit = makeSwitch()
    val roundedCorners = makeSwitch()
    val relativeTo = makeSpinner()

    init {
        addItem("Text color", textColor)
        addItem("Unit", unit)
        addItem("Show unit", showUnit)
        addItem("Rounded corners", roundedCorners)
        addItem("Relative to", relativeTo)
    }
}