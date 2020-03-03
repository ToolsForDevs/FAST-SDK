package com.toolsfordevs.fast.plugins.logger.ui.renderer

import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.ext.*
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setPaddingEnd
import com.toolsfordevs.fast.core.ui.ext.setPaddingStart
import com.toolsfordevs.fast.core.ui.ext.setPaddingVertical
import com.toolsfordevs.fast.modules.recyclerview.Renderer
import com.toolsfordevs.fast.core.util.ColorUtil
import com.toolsfordevs.fast.plugins.logger.R
import com.toolsfordevs.fast.plugins.logger.ui.items.LogSessionEndWrapper
import com.toolsfordevs.fast.plugins.logger.ui.items.LogSessionStartWrapper


internal class LogSessionStartRenderer(parent: ViewGroup) : Renderer<LogSessionStartWrapper>(hLinearLayout(parent.context))
{
    private val name = TextView(parent.context).apply {
        textSize = 12f
        setTextColor(MaterialColor.WHITE_100)
        setPaddingVertical(4.dp)

    }
    private val point = StartPointView(parent.context).apply { isUpDirection = false }

    init
    {
        (itemView as LinearLayout).apply {
            setPaddingStart(4.dp)
            setPaddingEnd(8.dp)
            setBackgroundColor(FastColor.colorAccent)
            layoutParams = layoutParamsMW()

            addView(point, layoutParamsVM(12.dp))
            addView(name, linearLayoutParamsMW().apply {
                marginStart = 4.dp
                gravity = Gravity.CENTER_VERTICAL
            })
        }
    }

    init {
        point.isUpDirection = false
    }

    override fun bind(data: LogSessionStartWrapper, position: Int)
    {
        point.setColor(
            if (ColorUtil.isColorDark(data.session.color))
                ColorUtil.lighten(data.session.color, 0.5f)
            else
                ColorUtil.darken(data.session.color, 0.5f))

        name.setTextColor(
            if (ColorUtil.isColorDark(data.session.color))
                Color.WHITE
            else
                Color.BLACK)

        val text = "[ Session Start - ${data.session.name} ]"
        name.text = text

        itemView.setBackgroundColor(data.session.color)
    }
}