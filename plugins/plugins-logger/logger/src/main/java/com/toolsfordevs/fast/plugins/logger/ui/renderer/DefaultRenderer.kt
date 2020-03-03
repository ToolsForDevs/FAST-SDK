package com.toolsfordevs.fast.plugins.logger.ui.renderer

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.TextView
import com.toolsfordevs.fast.core.ext.dp
import com.toolsfordevs.fast.core.ext.layoutParamsMW
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.modules.formatters.FastFormatter
import com.toolsfordevs.fast.plugins.logger.modules.ext.LogRenderer


internal class DefaultRenderer(parent:ViewGroup) : LogRenderer<FastFormatter>(TextView(parent.context))
{
    protected val textView: TextView = itemView  as TextView

    init
    {
        textView.apply {
            setPadding(0, 8.dp, 8.dp, 8.dp)
            typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
            textSize = 10f
            setTextColor(MaterialColor.BLACK_100)
            layoutParams = layoutParamsMW()
        }

        // ToDo Selectable prevents clickable
        // add a menu entry "copy content"
        //textView.setTextIsSelectable(true)
    }

    override fun bind(data: Any?, formatter: FastFormatter)
    {
        textView.text = formatter.formatToString(data)
    }
}