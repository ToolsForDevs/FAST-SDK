package com.toolsfordevs.fast.modules.resourcepicker.dimens

import android.content.Context
import android.widget.Space
import com.toolsfordevs.fast.modules.resourcepicker.*
import com.toolsfordevs.fast.modules.resources.DimensionResource


internal class FavoriteDimensionsPicker(context: Context) : ResourcePickerView(context)
{
    private var viewDelegate: DimensViewDelegate
    private var listener: ((dimensionRes: DimensionResource) -> Unit)? = null

    private val filterDelegate:FilterDelegate

    private val callback: () -> Unit = { refresh() }

    init
    {
        buttonBar.addView(Space(context), LayoutParams(0, LayoutParams.WRAP_CONTENT).apply { weight = 1f })

        val filterButton = addButton(R.drawable.fast_resourcepicker_ic_filter)
        val dimensionButton = addButton("DEF")
        //addButton(R.drawable.fast_resourcepicker_ic_search)

        viewDelegate = DimensViewDelegate(recyclerView) { dimensionRes -> listener?.invoke(dimensionRes) }

        val dimensionDelegate = DimensionDelegate(dimensionButton) { dimensionType ->
            viewDelegate.dimensionUnit = dimensionType
        }

        filterDelegate = FilterDelegate(filterButton) { data: List<Any> ->
            viewDelegate.setData(data)
        }

        viewDelegate.dimensionUnit = dimensionDelegate.dimensionType
    }

    private fun refresh()
    {
        filterDelegate.updateResources(Prefs.favoriteDimensions)
    }

    fun setOnDimensionSelectedListener(callback: (dimensionRes: DimensionResource) -> Unit)
    {
        listener = callback
    }

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()
        FavoriteManager.addOnFavoriteChangeCallback(callback)
        refresh()
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        FavoriteManager.removeOnFavoriteChangeCallback(callback)
    }
}