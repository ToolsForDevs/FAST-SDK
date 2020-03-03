package com.toolsfordevs.fast.modules.resourcepicker.drawable

import android.content.Context
import android.graphics.Color
import android.widget.Space
import com.toolsfordevs.fast.modules.resourcepicker.*
import com.toolsfordevs.fast.modules.resources.DrawableResource


internal class FavoriteDrawablePicker(context: Context) : ResourcePickerView(context)
{
    private var listener: ((drawableRes: DrawableResource) -> Unit)? = null

    private val filterDelegate:FilterDelegate
    private val callback: () -> Unit = {
        refresh() }

    init
    {

        val colorButton = addButton(R.drawable.fast_resourcepicker_ic_palette)

        buttonBar.addView(Space(context), LayoutParams(0, LayoutParams.WRAP_CONTENT).apply { weight = 1f })

        val stateButton = addButton(R.drawable.fast_resourcepicker_ic_state)
        val filterButton = addButton(R.drawable.fast_resourcepicker_ic_filter)
        val viewButton = addButton(R.drawable.fast_resourcepicker_ic_view_list)
        //addButton(R.drawable.fast_resourcepicker_ic_search)

        val viewDelegate = DrawableViewDelegate(viewButton, recyclerView, Prefs::favoriteDrawablePickerMode) { drawableRes -> listener?.invoke(drawableRes) }

        val backgroundColorDelegate =
                BackgroundColorDelegate(colorButton) { color ->
                    viewDelegate.setColorTheme(color)
                }

        val stateDelegate =
                DrawableStateDelegate(stateButton) { states: IntArray? ->
                    viewDelegate.setDrawableStates(states)
                }

        filterDelegate = FilterDelegate(filterButton) { data: List<Any> ->
            viewDelegate.setData(data)
        }

        viewDelegate.setColorTheme(Color.WHITE)
    }

    fun setOnDrawableSelectedListener(callback: (drawableRes: DrawableResource) -> Unit)
    {
        listener = callback
    }

    private fun refresh()
    {
        filterDelegate.updateResources(Prefs.favoriteDrawables)
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