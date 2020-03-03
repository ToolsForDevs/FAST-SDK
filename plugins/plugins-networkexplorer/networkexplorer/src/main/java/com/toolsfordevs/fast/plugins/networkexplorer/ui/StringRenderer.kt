package com.toolsfordevs.fast.plugins.networkexplorer.ui

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.toolsfordevs.fast.core.ext.dp
import com.toolsfordevs.fast.core.ext.layoutParamsMW
import com.toolsfordevs.fast.core.ui.MaterialColor
import com.toolsfordevs.fast.core.ui.ext.setPadding
import com.toolsfordevs.fast.core.util.CopyUtil
import com.toolsfordevs.fast.modules.recyclerview.ViewRenderer
import com.toolsfordevs.fast.plugins.networkexplorer.R

internal class StringRenderer(parent: ViewGroup) : ViewRenderer<String, TextView>(TextView(parent.context)), View.OnClickListener
{
    init
    {
        view.setPadding(8.dp)
        view.textSize = 10f
        view.typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
        view.setTextColor(MaterialColor.BLACK_87)
        view.layoutParams = layoutParamsMW()
        view.setBackgroundResource(R.drawable.fast_selectable_item_background)
        view.setOnClickListener(this)
    }

    override fun bind(data: String, position: Int)
    {
        view.text = data
    }

    override fun onClick(v: View?)
    {
        val data = adapterDelegate!!.getItem(adapterPosition) as String
        
        CopyUtil.copy("Body", data)
    }
}