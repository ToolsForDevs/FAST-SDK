package com.toolsfordevs.fast.modules.resourcepicker.color

import android.content.Context
import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.resourcepicker.Prefs
import com.toolsfordevs.fast.modules.resources.ColorResource
import com.toolsfordevs.fast.core.ui.widget.FastTabbedDialog
import com.toolsfordevs.fast.core.ui.widget.adapter.FastViewPagerAdapter

@Keep
class ColorPickerDialog(context: Context, val callback: (ColorResource) -> Unit, val color:Int? = null) : FastTabbedDialog(context, Prefs::colorPickerSelectedTab)
{
    init
    {
        color?.let { (viewPager.adapter as ColorPagerAdapter).setColor(it) }
    }

    override fun buildViewPagerAdapter(context: Context): FastViewPagerAdapter
    {
        return ColorPagerAdapter(context) { colorResource ->
            callback(colorResource)
            dismiss()
        }
    }

    private class ColorPagerAdapter(context:Context, callback: (ColorResource) -> Unit) : FastViewPagerAdapter()
    {
        private val view1 = ColorResourcePicker(context).apply { setOnColorSelectedListener(callback) }
        private val view2 = MaterialColorPicker(context).apply { setOnColorSelectedListener(callback) }
        private val view3 = FavoriteColorsPicker(context).apply { setOnColorSelectedListener(callback) }
        private val view4 = FastColorPickerView(context).apply { setOnColorSelectedListener(callback) }

        override fun getItem(position: Int): View
        {
            return when (position)
            {
                0 -> view1
                1 -> view2
                2 -> view3
                else -> view4
            }
        }

        fun setColor(color:Int)
        {
            view4.setColor(color)
        }

        override fun getCount(): Int = 4

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position)
            {
                0 -> "Color Resources"
                1 -> "Material Colors"
                2 -> "Favorites"
                else -> "Custom Color"
            }
        }
    }
}