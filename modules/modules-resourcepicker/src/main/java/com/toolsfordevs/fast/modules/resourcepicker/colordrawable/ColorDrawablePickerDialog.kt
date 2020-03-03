package com.toolsfordevs.fast.modules.resourcepicker.colordrawable

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.toolsfordevs.fast.core.annotation.Keep
import com.toolsfordevs.fast.modules.resourcepicker.Prefs
import com.toolsfordevs.fast.modules.resourcepicker.color.ColorResourcePicker
import com.toolsfordevs.fast.modules.resourcepicker.color.FastColorPickerView
import com.toolsfordevs.fast.modules.resourcepicker.color.FavoriteColorsPicker
import com.toolsfordevs.fast.modules.resourcepicker.color.MaterialColorPicker
import com.toolsfordevs.fast.modules.resourcepicker.drawable.DrawableResourcePicker
import com.toolsfordevs.fast.modules.resourcepicker.drawable.FavoriteDrawablePicker
import com.toolsfordevs.fast.modules.resources.ColorResource
import com.toolsfordevs.fast.modules.resources.DrawableResource
import com.toolsfordevs.fast.modules.resources.TypedResource
import com.toolsfordevs.fast.core.ui.widget.FastTabbedDialog
import com.toolsfordevs.fast.core.ui.widget.adapter.FastViewPagerAdapter
import com.toolsfordevs.fast.core.util.ResUtils
import java.lang.Exception

@Keep
class ColorDrawablePickerDialog(context: Context, val callback: (colorOrDrawable: DrawableResource) -> Unit) :
        FastTabbedDialog(context, Prefs::colorDrawablePickerSelectedTab)
{
    override fun buildViewPagerAdapter(context: Context): FastViewPagerAdapter
    {
        return ColorDrawablePagerAdapter(context) { drawableResource ->
            callback(drawableResource)
            dismiss()
        }
    }

    private class ColorDrawablePagerAdapter(context: Context, callback: (drawableResource: DrawableResource) -> Unit) : FastViewPagerAdapter()
    {
        private val drawableCallback: (drawableResource: DrawableResource) -> Unit = { callback(it) }
        private val colorCallback: (colorResource: ColorResource) -> Unit = {
            val drawable = ColorDrawable(it.value ?: try { ResUtils.getColor(it.resId) } catch (e:Exception) { 0xFF000000.toInt() })
            callback(DrawableResource(it.name, value = drawable))
        }

        private val view1 = ColorResourcePicker(context).apply { setOnColorSelectedListener(colorCallback) }
        private val view2 = MaterialColorPicker(context).apply { setOnColorSelectedListener(colorCallback) }
        private val view3 = FavoriteColorsPicker(context).apply { setOnColorSelectedListener(colorCallback) }
        private val view4 = FastColorPickerView(context).apply { setOnColorSelectedListener(colorCallback) }
        private val view5 = DrawableResourcePicker(context).apply { setOnDrawableSelectedListener(drawableCallback) }
        private val view6 = FavoriteDrawablePicker(context).apply { setOnDrawableSelectedListener(drawableCallback) }

        override fun getItem(position: Int): View
        {
            return when (position)
            {
                0    -> view1
                1    -> view2
                2    -> view3
                3    -> view4
                4    -> view5
                else -> view6
            }
        }

        override fun getCount(): Int = 6

        override fun getPageTitle(position: Int): CharSequence?
        {
            return when (position)
            {
                0    -> "Color Resources"
                1    -> "Material Colors"
                2    -> "Favorites Colors"
                3    -> "Custom Color"
                4    -> "Drawable Resources"
                else -> "Favorites Drawables"
            }
        }
    }
}