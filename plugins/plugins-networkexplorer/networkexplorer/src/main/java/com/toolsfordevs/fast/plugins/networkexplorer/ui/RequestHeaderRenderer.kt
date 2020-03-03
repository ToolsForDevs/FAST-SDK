package com.toolsfordevs.fast.plugins.networkexplorer.ui

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.toolsfordevs.fast.core.ext.dp
import com.toolsfordevs.fast.core.ext.hLinearLayout
import com.toolsfordevs.fast.core.ext.layoutParamsMW
import com.toolsfordevs.fast.core.ext.linearLayoutParamsWeW
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setPaddingHorizontal
import com.toolsfordevs.fast.core.ui.ext.setPaddingStart
import com.toolsfordevs.fast.core.util.CopyUtil
import com.toolsfordevs.fast.modules.recyclerview.ViewRenderer
import com.toolsfordevs.fast.plugins.networkexplorer.R

internal class RequestHeaderRenderer(parent: ViewGroup) : ViewRenderer<RequestHeader, LinearLayout>(hLinearLayout(parent.context)), View.OnClickListener
{
    private val name: TextView = TextView(parent.context).apply {
        typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
        textSize = 12f
        setTextColor(MaterialColor.BLACK_87)
    }
    private val value: TextView = TextView(parent.context).apply {
        typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
        textSize = 12f
        setPaddingStart(16.dp)
        setTextColor(MaterialColor.BLACK_87)
    }

    init
    {
        view.addView(name, linearLayoutParamsWeW(0.3f))
        view.addView(value, linearLayoutParamsWeW(0.7f))
        view.setPaddingHorizontal(8.dp)

        view.layoutParams = layoutParamsMW()
        view.setBackgroundResource(R.drawable.fast_selectable_item_background)
        view.setOnClickListener(this)
    }

    override fun bind(data: RequestHeader, position: Int)
    {
        name.text = data.name
        value.text = data.value
    }

    override fun onClick(v: View?)
    {
        val header = adapterDelegate!!.getItem(adapterPosition) as RequestHeader
        CopyUtil.copy(header.name, header.value)
    }
}