package com.toolsfordevs.fast.modules.subheader

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.toolsfordevs.fast.core.FastColor
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.recyclerview.ExpandableRenderer
import com.toolsfordevs.fast.core.ui.ext.setTint
import com.toolsfordevs.fast.core.ui.ext.show

@Keep
class ExpandableSubheaderRenderer(parent: ViewGroup) : ExpandableRenderer<Subheader>(parent, R.layout.fast_subheader_item_expandable_subheader)
{
    companion object
    {
        var color = Color.BLACK
    }

    private val text: TextView = itemView.findViewById(R.id.text)
    private val icon: ImageView = itemView.findViewById(R.id.icon)
    private val divider: View = itemView.findViewById(R.id.fast_divider)

    override fun bind(data: Subheader, position: Int)
    {
        text.text = data.title
        icon.rotation = if (isExpanded()) 180f else 0f

        text.setTextColor(if (isExpanded()) FastColor.colorAccent else color)
        icon.setTint(color)

        divider.show(shouldShowDivider())

        itemView.setOnClickListener {
            icon.animate().rotation(if (isExpanded()) 0f else   180f).start()
            text.setTextColor(if (isExpanded()) color else FastColor.colorAccent)
            toggle()
            divider.show(shouldShowDivider())
        }
    }
}