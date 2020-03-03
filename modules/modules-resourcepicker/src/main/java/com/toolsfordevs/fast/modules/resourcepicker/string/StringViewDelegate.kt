package com.toolsfordevs.fast.modules.resourcepicker.string

import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.modules.resourcepicker.BaseViewDelegate
import com.toolsfordevs.fast.modules.resourcepicker.R
import com.toolsfordevs.fast.modules.recyclerview.Renderer
import com.toolsfordevs.fast.modules.resourcepicker.FavoriteManager
import com.toolsfordevs.fast.modules.resources.StringResource
import java.util.*


internal class StringViewDelegate(recyclerView: RecyclerView, private val callback: (stringRes: StringResource) -> Unit) :
        BaseViewDelegate(recyclerView)
{
    var locale = Locale.getDefault()
        set (value)
        {
            field = value
            adapter.notifyDataSetChanged()
        }

    init
    {
        adapter.addRenderer(::StringRenderer)
    }

    private inner class StringRenderer(parent: ViewGroup) : Renderer<StringResource>(parent, R.layout.fast_resourcepicker_item_string),
                                                            View.OnClickListener
    {
        private val icon: ImageButton = itemView.findViewById(R.id.icon)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val value: TextView = itemView.findViewById(R.id.value)

        init
        {
            itemView.setOnClickListener(this)
        }

        override fun bind(data: StringResource, position: Int)
        {
            icon.isSelected = FavoriteManager.isFavorite(data)

            icon.setOnClickListener {
                icon.isSelected = !icon.isSelected
                FavoriteManager.toggleResource(data)
            }

            name.text = data.name
            value.text = ResUtils.getLocalizedString(itemView.context, locale, data.resId)
        }

        override fun onClick(v: View?)
        {
            val stringRes: StringResource = getItem(adapterPosition) as StringResource
            callback(stringRes)
        }
    }
}