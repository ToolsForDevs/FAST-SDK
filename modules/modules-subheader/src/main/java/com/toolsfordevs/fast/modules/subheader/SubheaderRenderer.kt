package com.toolsfordevs.fast.modules.subheader

import android.view.ViewGroup
import android.widget.TextView
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.recyclerview.Renderer

@Keep
class SubheaderRenderer(parent: ViewGroup) : Renderer<Subheader>(parent, R.layout.fast_subheader_item_subheader)
{
    private val title: TextView = itemView as TextView

    override fun bind(data: Subheader, position: Int)
    {
        title.text = data.title
    }
}