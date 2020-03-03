package com.toolsfordevs.fast.modules.resourcepicker.dimens

import android.content.Context
import android.widget.Space
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.resourcepicker.FilterDelegate
import com.toolsfordevs.fast.modules.resourcepicker.R
import com.toolsfordevs.fast.modules.resourcepicker.ResourcePickerView
import com.toolsfordevs.fast.modules.resources.ResourceManager
import com.toolsfordevs.fast.modules.resources.DimensionResource

@Keep
internal class DimensResourcePicker(context: Context) : ResourcePickerView(context)
{
    private var viewDelegate: DimensViewDelegate
    private var listener: ((dimensionRes: DimensionResource) -> Unit)? = null

    init
    {
        buttonBar.addView(Space(context), LayoutParams(0, LayoutParams.WRAP_CONTENT).apply { weight = 1f })

        val filterButton = addButton(R.drawable.fast_resourcepicker_ic_filter)
        val dimensionButton = addButton("DD")
        //addButton(R.drawable.fast_resourcepicker_ic_search)

        viewDelegate = DimensViewDelegate(recyclerView) { dimensionRes -> listener?.invoke(dimensionRes) }

        val dimensionDelegate = DimensionDelegate(dimensionButton) { dimensionType ->
            viewDelegate.dimensionUnit = dimensionType
        }

        val filterDelegate = FilterDelegate(filterButton) { data: List<Any> ->
            viewDelegate.setData(data)
        }

        viewDelegate.dimensionUnit = dimensionDelegate.dimensionType
        filterDelegate.updateResources(ResourceManager.dimens)
    }

    fun setOnDimensionSelectedListener(callback: (dimensionRes: DimensionResource) -> Unit)
    {
        listener = callback
    }
}