package com.toolsfordevs.fast.modules.resourcepicker.color

import android.content.Context
import android.graphics.Color
import android.widget.Space
import com.toolsfordevs.fast.modules.resourcepicker.*
import com.toolsfordevs.fast.modules.resources.ColorResource


internal class FavoriteColorsPicker(context: Context) : ResourcePickerView(context)
{
    private var viewDelegate: ColorViewDelegate
    private var listener: ((ColorResource) -> Unit)? = null

    private val filterDelegate:FilterDelegate
    private val callback: () -> Unit = { refresh() }

    init
    {

        val colorButton = addButton(R.drawable.fast_resourcepicker_ic_palette)

        buttonBar.addView(Space(context), LayoutParams(0, LayoutParams.WRAP_CONTENT).apply { weight = 1f })

        val stateButton = addButton(R.drawable.fast_resourcepicker_ic_state)
        val filterButton = addButton(R.drawable.fast_resourcepicker_ic_filter)
        val viewButton = addButton(R.drawable.fast_resourcepicker_ic_view_list)
        //addButton(R.drawable.fast_resourcepicker_ic_search)

        viewDelegate = ColorViewDelegate(viewButton, recyclerView, Prefs::favoriteColorPickerMode) { color -> listener?.invoke(color) }

        val backgroundColorDelegate = BackgroundColorDelegate(colorButton) { color ->
            viewDelegate.setColorTheme(color)
        }

        val stateDelegate = DrawableStateDelegate(stateButton) { states: IntArray? ->
            viewDelegate.setDrawableStates(states)
        }

        filterDelegate = FilterDelegate(filterButton) { data: List<Any> ->
            viewDelegate.setData(data)
        }

        viewDelegate.setColorTheme(Color.WHITE)
    }

    fun setOnColorSelectedListener(callback: (ColorResource) -> Unit)
    {
        listener = callback
    }

    private fun refresh()
    {
        filterDelegate.updateResources(Prefs.favoriteColors, false)
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