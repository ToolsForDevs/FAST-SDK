package com.toolsfordevs.fast.modules.resourcepicker.dimens

import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.toolsfordevs.fast.core.util.Dimens
import com.toolsfordevs.fast.core.util.ResUtils
import com.toolsfordevs.fast.modules.androidx.recyclerview.widget.RecyclerView
import com.toolsfordevs.fast.modules.recyclerview.Renderer
import com.toolsfordevs.fast.modules.resourcepicker.BaseViewDelegate
import com.toolsfordevs.fast.modules.resourcepicker.FavoriteManager
import com.toolsfordevs.fast.modules.resourcepicker.R
import com.toolsfordevs.fast.modules.resources.DimensionResource


internal class DimensViewDelegate(recyclerView: RecyclerView, private val callback: (dimensionRes: DimensionResource) -> Unit) :
        BaseViewDelegate(recyclerView)
{
    var dimensionUnit: Int = DimensionDelegate.DEFAULT_UNIT
        set(value)
        {
            field = value

            dimensionLabel = when (field)
            {
                TypedValue.COMPLEX_UNIT_MM     -> "MM"
                TypedValue.COMPLEX_UNIT_IN     -> "IN"
                TypedValue.COMPLEX_UNIT_PT     -> "PT"
                TypedValue.COMPLEX_UNIT_PX     -> "PX"
                TypedValue.COMPLEX_UNIT_SP     -> "SP"
                TypedValue.COMPLEX_UNIT_DIP    -> "DP"
                else                           -> ""
            }.toLowerCase()

            adapter.notifyDataSetChanged()
        }

    var dimensionLabel = "dp"

    init
    {
        adapter.addRenderer(::DimensionRenderer)
    }

    private inner class DimensionRenderer(parent: ViewGroup) :
            Renderer<DimensionResource>(parent, R.layout.fast_resourcepicker_item_dimens),
            View.OnClickListener
    {
        private val icon: ImageButton = itemView.findViewById(R.id.icon)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val value: TextView = itemView.findViewById(R.id.value)

        init
        {
            itemView.setOnClickListener(this)
        }

        override fun bind(data: DimensionResource, position: Int)
        {
            icon.isSelected = FavoriteManager.isFavorite(data)

            icon.setOnClickListener {
                icon.isSelected = !icon.isSelected
                FavoriteManager.toggleResource(data)
            }

            name.text = data.name

            var success = false

            if (dimensionUnit != DimensionDelegate.DEFAULT_UNIT)
            {
                try
                {
                    val dimensInPixels = data.value ?: ResUtils.getDimension(data.resId)
                    val convertedDimens = Dimens.pxToDimension(dimensInPixels, dimensionUnit)

                    val text = "$convertedDimens $dimensionLabel"
                    value.text = text
                    success = true
                }
                catch (e: Exception)
                {
//                    Log.d("DinsViewDelegate", "couldn't fetch dimension resource ${data.name}")
                }
            }

            if (!success)
            {
                try
                {
                    val outValue = TypedValue()
                    itemView.context.resources.getValue(data.resId, outValue, true)
                    value.text = TypedValue.coerceToString(outValue.type, outValue.data)
                    success = true
                }
                catch (e: Exception)
                {

                }
            }

            if (!success)
                value.text = "???"
        }

        override fun onClick(v: View?)
        {
            val dimensionRes: DimensionResource = getItem(adapterPosition) as DimensionResource
            callback(dimensionRes)
        }
    }
}