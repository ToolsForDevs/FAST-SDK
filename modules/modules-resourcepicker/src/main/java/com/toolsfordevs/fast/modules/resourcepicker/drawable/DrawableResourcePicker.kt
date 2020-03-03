package com.toolsfordevs.fast.modules.resourcepicker.drawable

import android.content.Context
import android.graphics.Color
import android.widget.Space
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.resources.ResourceManager
import com.toolsfordevs.fast.modules.resourcepicker.*
import com.toolsfordevs.fast.modules.resources.DrawableResource

@Keep
internal class DrawableResourcePicker(context: Context) : ResourcePickerView(context)
{
    private var viewDelegate: DrawableViewDelegate
    private var listener: ((drawableRes: DrawableResource) -> Unit)? = null

    init
    {

        val colorButton = addButton(R.drawable.fast_resourcepicker_ic_palette)

        buttonBar.addView(Space(context), LayoutParams(0, LayoutParams.WRAP_CONTENT).apply { weight = 1f })

        val stateButton = addButton(R.drawable.fast_resourcepicker_ic_state)
        val filterButton = addButton(R.drawable.fast_resourcepicker_ic_filter)
        val viewButton = addButton(R.drawable.fast_resourcepicker_ic_view_list)
        //addButton(R.drawable.fast_resourcepicker_ic_search)

        viewDelegate = DrawableViewDelegate(viewButton, recyclerView, Prefs::resourceDrawablePickerMode) { drawableRes -> listener?.invoke(drawableRes) }

        val backgroundColorDelegate =
                BackgroundColorDelegate(colorButton) { color ->
                    viewDelegate.setColorTheme(color)
                }

        val stateDelegate =
                DrawableStateDelegate(stateButton) { states: IntArray? ->
                    viewDelegate.setDrawableStates(states)
                }

        val filterDelegate = FilterDelegate(filterButton) { data: List<Any> ->
            viewDelegate.setData(data)
        }

        viewDelegate.setColorTheme(Color.WHITE)
        filterDelegate.updateResources(ResourceManager.drawables)
    }

    fun setOnDrawableSelectedListener(callback: (drawableRes: DrawableResource) -> Unit)
    {
        listener = callback
    }
}