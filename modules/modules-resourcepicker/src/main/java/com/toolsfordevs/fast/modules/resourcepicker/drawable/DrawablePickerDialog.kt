package com.toolsfordevs.fast.modules.resourcepicker.drawable

import android.content.Context
import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.resourcepicker.Prefs
import com.toolsfordevs.fast.modules.resources.DrawableResource
import com.toolsfordevs.fast.core.ui.widget.FastTabbedDialog
import com.toolsfordevs.fast.core.ui.widget.adapter.FastViewPagerAdapter

@Keep
class DrawablePickerDialog(context: Context, val callback: (drawableRes: DrawableResource) -> Unit) : FastTabbedDialog(context, Prefs::drawablePickerSelectedTab)
{
    override fun buildViewPagerAdapter(context: Context): FastViewPagerAdapter
    {
        return DrawablePagerAdapter(context) { drawableRes ->
            callback(drawableRes)
            dismiss()
        }
    }

    private class DrawablePagerAdapter(context: Context, callback: (drawableRes: DrawableResource) -> Unit) : FastViewPagerAdapter()
    {
        private val view1 = DrawableResourcePicker(context).apply { setOnDrawableSelectedListener(callback) }
        private val view2 = FavoriteDrawablePicker(context).apply { setOnDrawableSelectedListener(callback) }

        override fun getItem(position: Int): View
        {
            return when (position)
            {
                0 -> view1
                1 -> view2
                else -> view1
            }
        }

        override fun getCount(): Int = 2

        override fun getPageTitle(position: Int): CharSequence?
        {
            return when (position)
            {
                0 -> "Drawable Resources"
                1 -> "Favorites"
                else -> "Material"
                // else -> "Custom"
            }
        }
    }
}